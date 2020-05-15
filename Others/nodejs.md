# Nodejs
Node.js 适合I/O密集型的应用，而不是计算密集型的应用， 因为一个Node.js进程只有一个线程，因此在任何时刻都只有一个事件在执行。

如果这个事件占用大量的CPU 时间，执行事件循环中的下一个事件就需要等待很久，因此Node.js的一个编程原则就是尽量缩短每个事件的执行时间。

## 1. 创建Node.js应用
1. 引入required模块：使用require指令来载入Node.js模块
2. 创建服务器：服务器可以监听客户端的请求，
3. 接收请求与响应请求：客户端可以使用浏览器或终端发送HTTP请求，服务器接收请求后返回响应数据。

## 2. NPM介绍
NPM是一种包管理工具。

## 3. Node.js回调函数
Node.js 异步编程的依托于回调函数来实现。回调函数在完成任务后就会被调用，Node使用大量的回调函数，Node的所有API都支持回调函数。比如说：我可以一边读取文件，一边执行其他命令，当文件读取完成后，将文件内容作为回调函数的参数返回。这样在执行代码时就没有阻塞或等待文件的I/O操作。提高了性能，可以处理大量的并发请求。

阻塞按是按顺序执行的，而非阻塞是不需要按顺序的，所以如果需要处理回调函数的参数，我们就需要写在回调函数内。

## 4. 事件循环
Node.js是单进程单线程应用程序，但是通过事件和回调支持并发，所以性能非常高。
Node.js使用事件驱动模型，当web server接收到请求，就把它关闭然后进行处理，然后去服务下一个web请求。当这个请求完成，它被放回处理队列，当到达队列开头，这个结果被返回给用户。这个模型非常高效可扩展性非常强，因为webserver一直接受请求而不等待任何读写操作。（这也被称之为非阻塞式IO或者事件驱动IO）。在事件驱动模型中，会生成一个主循环来监听事件，当检测到事件时触发回调函数。Node.js 基本上所有的事件机制都是用设计模式中观察者模式实现。

1. 引入 events 模块
2. 创建 eventEmitter 对象
3. 绑定事件及事件的处理程序
4. 触发事件

## 5. 事件
Node.js 所有的异步 I/O 操作在完成时都会发送一个事件到事件队列。

EventEmitter介绍： EventEmitter的每个事件由一个事件名和若干个参数组成，事件名是一个字符串，通常表达一定的语义。对于每个事件，EventEmitter支持若干个事件监听器。当事件发射时，注册到这个事件的事件监听器被依次调用，事件参数作为回调函数参数传递。

EventEmitter.on(event, listener)、emitter.addListener(event, listener) 为指定事件注册一个监听器，接收一个字符串 event 和一个回调函数 listener。

大多数时候我们不会直接使用EventEmitter，而是在对象中继承它。包括 fs、net、 http在内的，只要是支持事件响应的核心模块都是EventEmitter的子类。首先，具有某个实体功能的对象实现事件符合语义， 事件的监听和发射应该是一个对象的方法。其次JavaScript 的对象机制是基于原型的，支持部分多重继承，继承EventEmitter不会打乱对象原有的继承关系。

## 6. Node.js Buffer(缓冲区)
Node.js中，定义了一个 Buffer 类，该类用来**创建一个专门存放二进制数据的缓存区**。Buffer 库为 Node.js 带来了一种存储原始数据的方法，可以让 Node.js 处理二进制数据，每当需要在 Node.js 中处理I/O操作中移动的数据时，就有可能使用 Buffer 库。

## 7. Node.js Stream(流)
从流中读取数据
```
var fs = require("fs");
var data = '';

// 创建可读流
var readerStream = fs.createReadStream('input.txt');

// 设置编码为 utf8。
readerStream.setEncoding('UTF8');

// 处理流事件 --> data, end, and error
readerStream.on('data', function(chunk) {
   data += chunk;
});

readerStream.on('end',function(){
   console.log(data);
});

readerStream.on('error', function(err){
   console.log(err.stack);
});

console.log("程序执行完毕");
```

写入流
```
var fs = require("fs");
var data = 'W3Cschool教程官网地址：www.w3cschool.cn';

// 创建一个可以写入的流，写入到文件 output.txt 中
var writerStream = fs.createWriteStream('output.txt');

// 使用 utf8 编码写入数据
writerStream.write(data,'UTF8');

// 标记文件末尾
writerStream.end();

// 处理流事件 --> data, end, and error
writerStream.on('finish', function() {
    console.log("写入完成。");
});

writerStream.on('error', function(err){
   console.log(err.stack);
});

console.log("程序执行完毕");
```

管道流（管道提供了一个输出流到输入流的机制。通常我们用于从一个流中获取数据并将数据传递到另外一个流中。）
```
var fs = require("fs");

// 创建一个可读流
var readerStream = fs.createReadStream('input.txt');

// 创建一个可写流
var writerStream = fs.createWriteStream('output.txt');

// 管道读写操作
// 读取 input.txt 文件内容，并将内容写入到 output.txt 文件中
readerStream.pipe(writerStream);

console.log("程序执行完毕");
```
链式流（链式是通过连接输出流到另外一个流并创建多个对个流操作链的机制，链式流一般用于管道操作）

```
ar fs = require("fs");
var zlib = require('zlib');

// 压缩 input.txt 文件为 input.txt.gz
fs.createReadStream('input.txt')
  .pipe(zlib.createGzip())
  .pipe(fs.createWriteStream('input.txt.gz'));
  
console.log("文件压缩完成。");
```

## 8. Node.js 模块系统
一个 Node.js 文件就是一个模块。Node.js 提供了exports 和 require 两个对象，其中 exports 是模块公开的接口，require 用于从外部获取一个模块的接口，即所获取模块的 exports 对象。

将函数进行封装
```
exports.world = function() {
  console.log('Hello World');
}
```

将对象进行封装
```
//hello.js 
function Hello() { 
    var name; 
    this.setName = function(thyName) { 
        name = thyName; 
    }; 
    this.sayHello = function() { 
        console.log('Hello ' + name); 
    }; 
}; 
module.exports = Hello;

//main.js 
var Hello = require('./hello'); 
hello = new Hello(); 
hello.setName('BYVoid'); 
hello.sayHello(); 
```

## 9. Node.js 函数
在JavaScript中，一个函数可以作为另一个函数接收一个参数。注意匿名函数的使用。

## 10. Node.js 路由
我们需要查看HTTP请求，从中提取出请求的URL以及GET/POST参数。随后路由需要根据这些数据来执行相应的代码。

## 11. Node.js 全局对象
global 最根本的作用是作为全局变量的宿主。按照 ECMAScript 的定义，满足以下条件的变量是全局变量：

1. 在最外层定义的变量；
2. 全局对象的属性；
3. 隐式定义的变量（未定义直接赋值的变量）。

在Node.js 中能够直接访问到对象通常都是 global 的属性，如 console、process 等

process.nextTick(callback)的功能是为事件循环设置一项任务，Node.js 会在 下次事件循环调响应时调用 callback。process.nextTick()可以把复杂的工作拆散，变成一个个较小的事件。

## 12 Web 模块
Web 应用架构
1. Client - 客户端，一般指浏览器，浏览器可以通过HTTP协议向服务器请求数据。
2. Server - 服务端，一般指Web服务器，可以接收客户端请求，并向客户端发送响应数据。
3. Business - 业务层， 通过Web服务器处理应用程序，如与数据库交互，逻辑运算，调用外部程序等。
4. Data - 数据层，一般由数据库组成。

## 13 Express框架
Express是一个简洁而灵活的node.js Web应用框架, 提供了一系列强大特性帮助你创建各种Web应用，和丰富的HTTP工具。

Express 框架核心特性包括：
1. 可以设置中间件来响应HTTP请求。
2. 定义了路由表用于执行不同的HTTP请求动作。
3. 可以通过向模板传递参数来动态渲染HTML页面。

Express应用使用回调函数的参数： request和response对象来处理请求和响应的数据。

路由
路由决定了由谁(指定脚本)去响应客户端请求。在HTTP请求中，我们可以通过路由提取出请求的URL以及GET/POST参数。

## 14 Node.js 多进程
Node.js本身是以单线程的模式运行的，但它使用的是事件驱动来处理并发，这样有助于我们在多核 cpu 的系统上创建多个子进程，从而提高性能。

每个子进程总是带有三个流对象：child.stdin, child.stdout和child.stderr。他们可能会共享父进程的stdio流，或者也可以是独立的被导流的流对象。

Node提供了child_process模块来创建子进程，方法有：

1. exec - child_process.exec使用子进程执行命令，缓存子进程的输出，并将子进程的输出以回调函数参数的形式返回。

2. spawn - child_process.spawn使用指定的命令行参数创建新进程。

3. fork - child_process.fork是spawn()的特殊形式，用于在子进程中运行的模块，如fork('./son.js')相当于spawn('node', ['./son.js']) 。与spawn方法不同的是，fork会在父进程与子进程之间，建立一个通信管道，用于进程之间的通信。