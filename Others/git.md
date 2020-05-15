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
第一步，用命令git add告诉Git，把文件添加到仓库：(执行命令，没有任何显示，说明添加成功, 注意：1. 必须进入文件所在的文件夹；2. add后面可以跟多个文件)
```
$ git add readme.md
```
第二步，用命令git commit告诉Git，把文件提交到仓库：
```
$ git commit -m "wrote a readme file"
```
**git commit**命令，**-m**后面输入的是本次提交的说明，可以输入任意内容，当然最好是有意义的，这样你就能从历史记录里方便地找到改动记录。

为什么有**add**和**commit**两步呢? 因为**git**有两个区域，工作区和暂存区，每次修改文件，文件会进入工作区。如果没有经过**add**，直接**commit**。Git 会提示你：**nothing to commit, working tree clean**
```
git add 就是把工作区的修改，提交到暂存区
git commit 把暂存区的修改，保存至本地库
git push 把本地库的记录，推送至远程库
```

## 4. 版本回退
在Git中，用**git log**命令查看历史修改记录：
```
$ git log
```
**git log**命令显示从最近到最远的提交日志。如果嫌输出信息太多，看得眼花缭乱的，可以试试加上--pretty=oneline参数：
```
$ git log --pretty=oneline
573a5b1210858855fc7d3f3f7a6ae021254aac1e add git nodes
933b85ddeca61f23441d683744e72f1752cf0c1c Create nodejs.md
763ef5cce28a9f3aafed6973f068e4dc8e710acf add code
90f073748ffedd6f68932617dabfb6a77f64ea36 Update README.md
adfaf24951fe7026083f93b937f81bd0c21bb21a Initial commit
```
最前面是使用SHA1计算出来的一个十六进制的版本号。

如果要回退回某一个版本，使用**git reset --hard commit_id**: 
```
$ git reset --hard 933b85
```
版本号没必要写全，前几位就可以了，Git会自动去找。Git的版本回退速度非常快，因为Git在内部有个指向当前版本的HEAD指针，当你回退版本的时候，Git仅仅是把HEAD指向修改的版本。

如果要恢复某个版本，使用如下命令：
```
$ git reflog
$ git reset --hard commit_id
```

## 5. 工作区和暂存区
```
$ git status 查看状态
```
工作区（Working Directory）：就是你在电脑里能看到的目录。

版本库（Repository）：工作区有一个隐藏目录 **.git**，这个不算工作区，而是Git的版本库。Git的版本库里存了很多东西，其中最重要的就是称为stage（或者叫index）的暂存区，还有Git为我们自动创建的第一个分支master（仓库），以及指向master的一个指针叫HEAD。

可以简单理解为，需要提交的文件修改通通放到暂存区，然后，一次性提交暂存区的所有修改。

**git add**命令实际上就是把要提交的所有修改放到暂存区（Stage），然后，执行**git commit**就可以一次性把暂存区的所有修改提交到分支（仓库）。一旦提交后，如果你又没有对工作区做任何修改，那么工作区就是“干净”的：

```
$ git status
On branch master
nothing to commit, working tree clean
```

```
Git管理的文件分为：工作区，版本库，版本库又分为暂存区stage和暂存区分支master(仓库)

工作区>>>>暂存区>>>>仓库

git status 查看工作区情况
git add 把文件从工作区>>>>暂存区
git commit 把文件从暂存区>>>>仓库
git diff 查看工作区和暂存区差异 git diff filename
git diff --cached 查看暂存区和仓库差异
git diff HEAD 查看工作区和仓库的差异 git diff HEAD -- filename
git add的反向命令git checkout，撤销工作区修改，即把暂存区最新版本转移到工作区 git checkout -- filename
git commit的反向命令git reset HEAD，就是把仓库最新版本转移到暂存区
```

## 6. 管理修改
**git**是严格遵守从工作区**add**到暂存区，再**commit**暂存区到分支

## 7. 撤销修改
**git checkout -- filename**可以丢弃工作区的修改，一般用于还未提交到暂存区的情况。如果已经添加到暂存区（git add），用命令**git reset HEAD filename**可以把暂存区的修改撤销掉（unstage），重新放回工作区。再使用**git checkout -- filename**撤销修改回到和版本库一模一样的状态。

命令**git checkout -- filename**意思就是，把**filename**文件在工作区的修改全部撤销，这里有两种情况：1. **filename**自修改后还没有被放到暂存区，现在，撤销修改就回到和版本库一模一样的状态；2. **filename**已经添加到暂存区后，又作了修改，现在，撤销修改就回到添加到暂存区后的状态。总之，就是让这个文件回到最近一次**git commit**或**git add**时的状态。

**git checkout -- file**命令中的 **--** 很重要，没有 **--**，就变成了“切换到另一个分支”的命令。

场景1：当你改乱了工作区某个文件的内容，想直接丢弃工作区的修改时，用命令**git checkout -- file**。

场景2：当你不但改乱了工作区某个文件的内容，还添加到了暂存区时，想丢弃修改，分两步，第一步用命令**git reset HEAD <file>**，就回到了场景1，第二步按场景1操作。

场景3：已经提交了不合适的修改到版本库时，想要撤销本次提交，已经**git commit**时，用**git reset**回退版本, 不过前提是没有推送到远程库。

场景4：推送到远程库，GG。

```
最新的命令为：
git resotre --worktree filename => git checkout -- filename （--worktree可省略）
git restore --staged filename => git reset HEAD filename
```

## 8. 删除文件
首先删除本地文件
```
rm filename
```
1. 一是确实要从版本库中删除该文件，那就用命令**git rm**删掉，并且**git commit**：
```
$ git rm filename
$ git commit -m "remove file"
```

2. 另一种情况是删错了，因为版本库里还有呢，所以可以很轻松地把误删的文件恢复到最新版本（注意这时只执行了rm test.txt，还没有提交）：
```
git checkout -- file
git resotre  file
```

## 9. 添加远程仓库


