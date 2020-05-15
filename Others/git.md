# Git快速学习
Git是目前世界上最先进的分布式版本控制系统

## 1. git安装
linux命令： 
```
sudo apt-get install git
```
安装完成后，还需要最后一步设置，在命令行输入：
```
$ git config --global user.name "Your Name"
$ git config --global user.emain "Your email"
```
因为Git是分布式版本控制系统，所以，每个机器都必须自报家门：你的名字和Email地址。你也许会担心，如果有人故意冒充别人怎么办？这个不必担心，首先我们相信大家都是善良无知的群众，其次，真的有冒充的也是有办法可查的。

注意**git config**命令的 **--global**参数，用了这个参数，表示你这台机器上所有的Git仓库都会使用这个配置，当然也可以对某个仓库指定不同的用户名和Email地址。

## 2. 创建版本库
版本库又名仓库，英文名repository，你可以简单理解成一个目录，这个目录里面的所有文件都可以被Git管理起来，每个文件的修改、删除，Git都能跟踪，以便任何时刻都可以追踪历史，或者在将来某个时刻可以“还原”。

首先，选择一个合适的地方，创建一个空目录：
```
$ mkdir learngit
$ cd learngit
$ pwd
/Users/michael/learngit
pwd命令用于显示当前目录。
```

第二步，通过**git init**命令把这个目录变成Git可以管理的仓库：
```
$ git init
Initialized empty Git repository in /Users/michael/learngit/.git/
```

可以发现当前目录下多了一个.git的目录，这个目录是Git来跟踪管理版本库的，没事千万不要手动修改这个目录里面的文件，不然改乱了，就把Git仓库给破坏了。如果没有看到.git目录，那是因为这个目录默认是隐藏的，用ls -ah命令就可以看见。

## 3. 将文件添加到仓库中
注意：所有的版本控制系统，其实只能跟踪文本文件的改动，比如TXT文件。而二进制文件的改动只能记录大小的变动。强烈建议使用标准的UTF-8编码，所有语言使用同一种编码，既没有冲突，又被所有平台所支持。

现在需要把一个**readme.md**添加到仓库中。
第一步，用命令git add告诉Git，把文件添加到仓库：(执行命令，没有任何显示，说明添加成功, 注意add后面可以跟多个文件)
```
$ git add readme.md
```
第二步，用命令git commit告诉Git，把文件提交到仓库：
```
$ git commit -m "wrote a readme file"
```
**git commit**命令，**-m**后面输入的是本次提交的说明，可以输入任意内容，当然最好是有意义的，这样你就能从历史记录里方便地找到改动记录。