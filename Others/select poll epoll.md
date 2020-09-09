# 一 概念说明

　　本文讨论的背景是**Linux**环境下的**network IO**。本文最重要的参考文献是**Richard Stevens**的“**UNIX® Network Programming Volume 1, Third Edition: The Sockets Networking** ”，6.2节“**I/O Models** ”，Stevens在这节中详细说明了各种IO的特点和区别，如果英文够好的话，推荐直接阅读。Stevens的文风是有名的深入浅出，所以不用担心看不懂。本文中的流程图也是截取自参考文献。参照[原文博客](https://segmentfault.com/a/1190000003063859)，仅供学习。

在进行解释之前，首先要说明几个概念：

- **用户空间和内核空间**
- **进程切换**
- **进程的阻塞**
- **文件描述符**
- **缓存 I/O**

## 用户空间与内核空间

　　现在操作系统都是采用虚拟存储器，那么对32位操作系统而言，它的寻址空间（虚拟存储空间）为**4G**（**2的32次方**）。操作系统的核心是内核，独立于普通的应用程序，可以访问受保护的内存空间，也有访问底层硬件设备的所有权限。为了保证用户进程不能直接操作内核（kernel），**保证内核的安全**，操心系统将虚拟空间划分为两部分，一部分为**内核空间**，一部分为**用户空间**。针对linux操作系统而言，将最高的1G字节（**从虚拟地址0xC0000000到0xFFFFFFFF**），供内核使用，称为**内核空间**，而将较低的3G字节（**从虚拟地址0x00000000到0xBFFFFFFF**），供各个进程使用，称为**用户空间**。

## 进程切换

　　为了控制进程的执行，内核必须有能力**挂起**正在CPU上运行的进程，并**恢复**以前挂起的某个进程的执行。这种行为被称为**进程切换**。因此可以说，任何进程都是在操作系统内核的支持下运行的，是与内核紧密相关的。

从一个进程的运行转到另一个进程上运行，这个过程中经过下面这些变化：

1.  **保存处理机上下文，包括程序计数器和其他寄存器。**
2. **更新PCB信息。**
3. **把进程的PCB移入相应的队列，如就绪、在某事件阻塞等队列。**
4. **选择另一个进程执行，并更新其PCB。**
5. **更新内存管理的数据结构。**
6. ***\**\**恢复处理机上下文。\**\**\***

注：**总而言之就是很耗资源**，具体的可以参考这篇文章：[进程切换](http://guojing.me/linux-kernel-architecture/posts/process-switch/)

## 进程的阻塞

　　正在执行的进程，由于**期待的某些事件未发生**，如**请求系统资源失败**、**等待某种操作的完成**、**新数据尚未到达或无新工作做**等，则由系统自动执行**阻塞**(**Block**)，使自己由**运行状态**变为**阻塞状态**。可见，进程的阻塞是进程自身的一种主动行为，也因此只有处于运行态的进程（获得CPU），才可能将其转为阻塞状态。

```
# 当进程进入阻塞状态，是不占用CPU资源的。　
```

## 文件描述符fd

　　**文件描述符**（**File descriptor**）是计算机科学中的一个术语，是一个用于表述指向文件的引用的抽象化概念。

　　文件描述符在形式上是一个**非负整数**。实际上，它是一个索引值，指向内核为每一个进程所维护的该进程打开文件的记录表。当程序打开一个现有文件或者创建一个新文件时，内核向进程返回一个**文件描述符**。在程序设计中，一些涉及底层的程序编写往往会围绕着文件描述符展开。但是文件描述符这一概念往往只适用于UNIX、Linux这样的操作系统。

## 缓存 I/O

　　**缓存 I/O** 又被称作**标准 I/O**，大多数文件系统的默认 I/O 操作都是**缓存 I/O**。在 Linux 的缓存 I/O 机制中，操作系统会将 I/O 的数据缓存在文件系统的**页缓存（ page cache ）**中，也就是说，数据会先被拷贝到**操作系统内核的缓冲区**中，然后才会从**操作系统内核的缓冲区**拷贝到**应用程序的地址空间**。

　　**缓存 I/O 的缺点：**
　　数据在传输过程中需要在**应用程序地址空间**和**内核**进行多次数据拷贝操作，这些数据拷贝操作所带来的 CPU 以及内存开销是非常大的。

## 总结

### blocking和non-blocking的区别

- 调用 **blocking IO** 会一直 **block** 住对应的进程直到操作完成，
- 而**non-blocking IO** 在 **kernel** 还准备数据的情况下会立刻返回。

### synchronous IO和asynchronous IO的区别

在说明**synchronous IO**和**asynchronous IO**的区别之前，需要先给出两者的定义。POSIX的定义是这样子的：

- **A synchronous I/O operation causes the requesting process to be blocked until that I/O operation completes;**
-  **An asynchronous I/O operation does not cause the requesting process to be blocked;**

两者的区别就在于**synchronous IO**做”**IO operation**”的时候会将**process**阻塞。

按照这个定义，之前所述的**blocking IO**，**non-blocking IO**，**IO multiplexing**都属于**synchronous IO**。

　　有人会说，**non-blocking IO**并没有被**block**啊。这里有个非常“狡猾”的地方，定义中所指的”**IO operation**”是指真实的IO操作，就是例子中的**recvfrom**这个**system call**。

- **non-blocking IO**在执行**recvfrom**这个**system call**的时候，如果**kernel**的数据没有准备好，这时候不会**block**进程。
- 但是，当**kernel**中数据准备好的时候，**recvfrom**会将数据从**kernel**拷贝到用户内存中，这个时候进程是被**block**了，在这段时间内，进程是被**block**的。
- 而**asynchronous IO**则不一样，当进程发起IO 操作之后，就直接返回再也不理睬了，直到**kernel**发送一个信号，告诉进程说**IO**完成。在这整个过程中，进程完全没有被**block**。

**各个IO Model的比较如图所示：**

![img](https://img2018.cnblogs.com/blog/1566397/201906/1566397-20190624181407936-1552295570.png)

通过上面的图片，可以发现**non-blocking IO** 和 **asynchronous IO** 的区别还是很明显的。

- 在**non-blocking IO**中，虽然进程大部分时间都不会被**block**，但是它仍然要求进程去主动的**check**，并且当数据准备完成以后，也需要进程主动的再次调用**recvfrom**来将数据拷贝到用户内存。
- 而**asynchronous IO**则完全不同。它就像是用户进程将整个IO操作交给了他人（**kernel**）完成，然后他人做完后**发信号**通知。在此期间，用户进程不需要去检查IO操作的状态，也不需要主动的去拷贝数据。



# **二 I/O 多路复用之select、poll、epoll详解**

**select**，**poll**，**epoll**都是**IO多路复用**的机制。I/O多路复用就是通过一种机制，一个进程可以监视多个描述符，一旦某个描述符就绪（一般是读就绪或者写就绪），能够通知程序进行相应的读写操作。但**select，poll，epoll**本质上都是同步I/O，因为他们都需要在读写事件就绪后自己负责进行读写，也就是说这个读写过程是阻塞的，而异步I/O则无需自己负责进行读写，异步I/O的实现会负责把数据从内核拷贝到**用户空间**。

## select

```
int select (int n, fd_set *readfds, fd_set *writefds, fd_set *exceptfds, struct timeval *timeout);
```

**select** 函数监视的文件描述符分3类，分别是**writefds、readfds、和exceptfds**。调用后select函数会阻塞，直到有描述副就绪（有数据 可读、可写、或者有except），或者超时（timeout指定等待时间，如果立即返回设为null即可），函数返回。当**select**函数返回后，可以 通过遍历fdset，来找到就绪的描述符。

**select**目前几乎在所有的平台上支持，其良好跨平台支持也是它的一个优点。**select**的一 个缺点在于单个进程能够监视的文件描述符的数量存在最大限制，在Linux上一般为1024，可以通过修改宏定义甚至重新编译内核的方式提升这一限制，但 是这样也会造成效率的降低。

## poll

```
int poll (struct pollfd *fds, unsigned int nfds, int timeout);
```

不同与select使用三个位图来表示三个fdset的方式，poll使用一个 pollfd的指针实现。

```
struct pollfd {
    int fd; /* file descriptor */
    short events; /* requested events to watch */
    short revents; /* returned events witnessed */
};
```

pollfd结构包含了要监视的event和发生的event，不再使用select“参数-值”传递的方式。同时，pollfd并没有最大数量限制（但是数量过大后性能也是会下降）。 和select函数一样，poll返回后，需要轮询pollfd来获取就绪的描述符。

> 从上面看，select和poll都需要在返回后，`通过遍历文件描述符来获取已经就绪的socket`。事实上，同时连接的大量客户端在一时刻可能只有很少的处于就绪状态，因此随着监视的描述符数量的增长，其效率也会线性下降。

## epoll

epoll是在2.6内核中提出的，是之前的select和poll的增强版本。相对于select和poll来说，epoll更加灵活，没有描述符限制。epoll使用一个文件描述符管理多个描述符，将用户关系的文件描述符的事件存放到内核的一个事件表中，这样在用户空间和内核空间的copy只需一次。

### epoll操作过程

epoll操作过程需要三个接口，分别如下：

```
int epoll_create(int size)；//创建一个epoll的句柄，size用来告诉内核这个监听的数目一共有多大
int epoll_ctl(int epfd, int op, int fd, struct epoll_event *event)；
int epoll_wait(int epfd, struct epoll_event * events, int maxevents, int timeout);
```

**1. int epoll_create(int size);**
创建一个epoll的句柄，size用来告诉内核这个监听的数目一共有多大，这个参数不同于select()中的第一个参数，给出最大监听的fd+1的值，`参数size并不是限制了epoll所能监听的描述符最大个数，只是对内核初始分配内部数据结构的一个建议`。
当创建好epoll句柄后，它就会占用一个fd值，在linux下如果查看/proc/进程id/fd/，是能够看到这个fd的，所以在使用完epoll后，必须调用close()关闭，否则可能导致fd被耗尽。

**2. int epoll_ctl(int epfd, int op, int fd, struct epoll_event \*event)；**
函数是对指定描述符fd执行op操作。

- **epfd**：是epoll_create()的返回值。
- **op**：表示op操作，用三个宏来表示：添加EPOLL_CTL_ADD，删除EPOLL_CTL_DEL，修改EPOLL_CTL_MOD。分别添加、删除和修改对fd的监听事件。
- **fd**：是需要监听的fd（文件描述符）
- **epoll_event**：是告诉内核需要监听什么事，struct epoll_event结构如下：

**3. int epoll_wait(int epfd, struct epoll_event \* events, int maxevents, int timeout);**
等待epfd上的io事件，最多返回maxevents个事件。
参数events用来从内核得到事件的集合，maxevents告之内核这个events有多大，这个maxevents的值不能大于创建epoll_create()时的size，参数timeout是超时时间（毫秒，0会立即返回，-1将不确定，也有说法说是永久阻塞）。该函数返回需要处理的事件数目，如返回0表示已超时。

### epoll总结

  在 select/poll中，进程只有在调用一定的方法后，内核才对所有监视的文件描述符进行扫描，而**epoll事先通过epoll_ctl()来注册一 个文件描述符，一旦基于某个文件描述符就绪时，内核会采用类似callback的回调机制，迅速激活这个文件描述符，当进程调用epoll_wait() 时便得到通知**。(`此处去掉了遍历文件描述符，而是通过监听回调的的机制`。这正是epoll的魅力所在。)

**epoll的优点主要是一下几个方面：**

- **监视的描述符数量不受限制，它所支持的FD上限是最大可以打开文件的数目，这个数字一般远大于2048,**
  - 举个例子,在1GB内存的机器上大约是10万左 右，具体数目可以**cat /proc/sys/fs/file-max**察看,一般来说这个数目和系统内存关系很大。
  - **select的最大缺点就是进程打开的fd是有数量限制的。这对 于连接数量比较大的服务器来说根本不能满足。**
  - 虽然也可以选择**多进程**的解决方案( Apache就是这样实现的)，不过虽然linux上面创建进程的代价比较小，但仍旧是不可忽视的，加上进程间数据同步远比不上线程间同步的高效，所以也不是一种完美的方案。
- **IO的效率不会随着监视fd的数量的增长而下降。****epoll**不同于**select**和**poll**轮询的方式，而是通过每个**fd**定义的回调函数来实现的。只有**就绪**的fd才会执行回调函数。

> 如果没有大量的**idle -connection**或者**dead-connection**，**epoll**的效率并不会比**select/poll**高很多，但是当遇到大量的**idle- connection**，就会发现epoll的效率大大高于select/poll。