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
首先, 在github中创建仓库。
第二步，在本地的learngit仓库下运行命令，
```
git remote add origin git@github.com:Flamewaker/DailySummary.git
git remote add origin https://github.com:Flamewaker/DailySummary.git
```
添加后，远程库的名字就是origin，这是Git默认的叫法，也可以改成别的，但是origin这个名字一看就知道是远程库。
下一步，就可以把本地库的所有内容推送到远程库上：
```
git push -u origin master
```

把本地库的内容推送到远程，用git push命令，实际上是把当前分支master推送到远程。

由于远程库是空的，我们第一次推送master分支时，加上了**-u**参数，Git不但会把本地的master分支内容推送的远程新的master分支，还会把本地的master分支和远程的master分支关联起来，在以后的推送或者拉取时就可以简化命令。

从现在起，只要本地作了提交，就可以通过命令：
```
$ git push origin master
```
当你第一次使用Git的clone或者push命令连接GitHub时，会得到一个警告,这是因为Git使用SSH连接，而SSH连接在第一次验证GitHub服务器的Key时，需要你确认GitHub的Key的指纹信息是否真的来自GitHub的服务器，输入yes回车即可。

GitHub配置SSH Key的目的是为了帮助我们在通过git提交代码时，不需要繁琐的验证过程，简化操作流程。[如何配置SSH Key](https://blog.csdn.net/u013778905/article/details/83501204)（git@github.com:Flamewaker/DailySummary.git）

## 10. 从远程库克隆
使用如下的命令：
```
git clone git@github.com:Flamewaker/DailySummary.git（ssh协议最快）
git clone https://github.com:Flamewaker/DailySummary.git (https协议)
```

## 11. 分支管理
分支在实际中有什么用呢？假设你准备开发一个新功能，但是需要两周才能完成，第一周你写了50%的代码，如果立刻提交，由于代码还没写完，不完整的代码库会导致别人不能干活了。如果等代码全部写完再一次提交，又存在丢失每天进度的巨大风险。

你创建了一个属于你自己的分支，别人看不到，还继续在原来的分支上正常工作，而你在自己的分支上干活，想提交就提交，直到开发完毕后，再一次性合并到原来的分支上，这样，既安全，又不影响别人工作。


## 12. 创建和合并分支
首先，我们创建dev分支，然后切换到dev分支：
```
$ git checkout -b dev
$ git branch 查看分支
```
git checkout命令加上-b参数表示创建并切换，相当于以下两条命令：
```
$ git branch dev
$ git checkout dev
```

当dev分支的工作完成，我们就可以切换回master分支：
```
$ git checkout master
```

我们把dev分支的工作成果合并到master分支上：
```
$ git merge dev
```

删除dev分支:
```
$ git branch -d dev
```

创建并切换到新的dev分支，可以使用：
```
$ git switch -c dev
```

直接切换到已有的master分支，可以使用：
```
$ git switch master
```

```
查看分支：git branch

创建分支：git branch <name>

切换分支：git checkout <name>或者git switch <name>

创建+切换分支：git checkout -b <name>或者git switch -c <name>

合并某分支到当前分支：git merge <name>

删除分支：git branch -d <name>
```
## 13. 解决冲突
**master**分支和**dev**分支各自都分别有新的提交。这种情况下，Git无法执行“快速合并”，只能试图把各自的修改合并起来，但这种合并就可能会有冲突。必须手动解决冲突后再提交。git status也可以告诉我们冲突的文件。
```
$ git merge dev
Auto-merging readme.txt
CONFLICT (content): Merge conflict in readme.txt
Automatic merge failed; fix conflicts and then commit the result.
```
Git用<<<<<<<，=======，>>>>>>>标记出不同分支的内容，我们在master分支中直接用cat查看readme.txt的内容，修改后保存。

再提交（手工修改之后，'git add readme.txt' 'git commit -m "confilict fixed" '，会自动将两个分支的文件合并）：
```
$ git add readme.txt 
$ git commit -m "conflict fixed"
[master cf810e4] conflict fixed
```

用带参数的git log也可以看到分支的合并情况：
```
$ git log --graph --pretty=oneline --abbrev-commit
```

## 14. 分支管理策略
通常，合并分支时，如果可能，Git会用Fast forward模式，但这种模式下，删除分支后，会丢掉分支信息。如果要强制禁用Fast forward模式，Git就会在merge时生成一个新的commit，这样，从分支历史上就可以看出分支信息。

```
git merge --no-ff -m "merge with no-ff" dev
```

在实际开发中，我们应该按照几个基本原则进行分支管理：

首先，master分支应该是非常稳定的，也就是仅用来发布新版本，平时不能在上面干活；

那在哪干活呢？干活都在dev分支上，也就是说，dev分支是不稳定的，到某个时候，比如1.0版本发布时，再把dev分支合并到master上，在master分支发布1.0版本；你和你的小伙伴们每个人都在dev分支上干活，每个人都有自己的分支，时不时地往dev分支上合并就可以了。

## 15. Bug分支
```
修复bug时，我们会通过创建新的bug分支进行修复，然后合并，最后删除；

当手头工作没有完成时，先把工作现场git stash一下，然后去修复bug，修复后，再git stash pop，回到工作现场；

在master分支上修复的bug，想要合并到当前dev分支，可以用git cherry-pick <commit>命令，把bug提交的修改“复制”到当前分支，避免重复劳动。
```

## 16. Feature分支
开发一个新feature，最好新建一个分支；所以，每添加一个新功能，最好新建一个feature分支，在上面开发，完成后，合并，最后，删除该feature分支。
当前在dev分支上进行开发。
```
$ git switch -c feature-101
```
开发完毕后。
```
$ git add vulcan.py
$ git commit -m "add feature vulcan"
```
此时还没有将分支合并上去。如果想撤销分支。
```
$ git branch -d feature-vulcan 会提示需要确定修改
$ git branch -D feature-vulcan 强制删除
```

## 17. 推送分支
查看远程库的信息，用**git remote**：
```
$ git remote
$ git remote -v 更详细
```
推送分支，就是把该分支上的所有本地提交推送到远程库。推送时，要指定本地分支，这样，Git就会把该分支推送到远程库对应的远程分支上：
```
$ git push origin dev (远程库branch name)
```

master分支是主分支，因此要时刻与远程同步；

dev分支是开发分支，团队所有成员都需要在上面工作，所以也需要与远程同步；

bug分支只用于在本地修复bug，就没必要推到远程了，除非老板要看看你每周到底修复了几个bug；

feature分支是否推到远程，取决于你是否和你的小伙伴合作在上面开发。

## 18. 抓取分支
首先在一个目录下克隆：
```
$ git clone git@github.com:michaelliao/learngit.git
```
创建远程origin的dev分支到本地：
```
$ git checkout -b dev origin/dev
```
若进行推送的时候发生了冲突，先用git pull把最新的提交从origin/dev抓下来，然后，在本地合并，解决冲突，再推送：
```
$ git pull
```
git pull也失败了，原因是没有指定本地dev分支与远程origin/dev分支的链接，根据提示，设置dev和origin/dev的链接：
```
git branch --set-upstream-to=origin/<branch> dev
```
再pull, 这回git pull成功，但是合并有冲突，需要手动解决，解决的方法和分支管理中的解决冲突完全一样。解决后，提交，再push：

因此，多人协作的工作模式通常是这样：

1. 首先，可以试图用git push origin <branch-name>推送自己的修改；

2. 如果推送失败，则因为远程分支比你的本地更新，需要先用git pull试图合并；

3. 如果合并有冲突，则解决冲突，并在本地提交；

4. 没有冲突或者解决掉冲突后，再用git push origin <branch-name>推送就能成功！

如果git pull提示no tracking information，则说明本地分支和远程分支的链接关系没有创建，用命令git branch --set-upstream-to <branch-name> origin/<branch-name>

```
查看远程库信息，使用git remote -v；

本地新建的分支如果不推送到远程，对其他人就是不可见的；

从本地推送分支，使用git push origin branch-name，如果推送失败，先用git pull抓取远程的新提交；

在本地创建和远程分支对应的分支，使用git checkout -b branch-name origin/branch-name，本地和远程分支的名称最好一致；

建立本地分支和远程分支的关联，使用git branch --set-upstream branch-name origin/branch-name；

从远程抓取分支，使用git pull，如果有冲突，要先处理冲突。
```

## 19. Rebase
将Git的提交历史变成一条干净的直线。把分叉的提交历史“整理”成一条直线，看上去更直观。缺点是本地的分叉提交已经被修改过了。最后，通过push操作把本地分支推送到远程。（先把刚才自己提交版本临时保存，再把版本更新到最新远程，再把临时保存的提交重新提交））

## 20. 标签
```
命令git tag <tagname>用于新建一个标签，默认为HEAD，也可以指定一个commit id；git tag <tagname> <commit id>

命令git tag -a <tagname> -m "blablabla..."可以指定标签信息；

命令git tag可以查看所有标签。

命令git push origin <tagname>可以推送一个本地标签；

命令git push origin --tags可以推送全部未推送过的本地标签；

命令git tag -d <tagname>可以删除一个本地标签；

如果标签已经推送到远程，要删除远程标签就麻烦一点，先从本地删除。然后，从远程删除。删除命令也是push。

命令git push origin :refs/tags/<tagname>可以删除一个远程标签。
```

## 21. 使用GitHub
```
在GitHub上，可以任意Fork开源仓库；

自己拥有Fork后的仓库的读写权限；

可以推送pull request给官方仓库来贡献代码。对方是否接受你的pull request就由官方来决定。
```