

# Linux三剑客学习笔记

https://blog.csdn.net/horseTom/article/details/95109277

## 正则表达式

学习三剑客之前我们首先要了解正则表达式，有了它能让你更强大。正则表达式在每种语言中都会有，功能就是匹配符合你预期要求的字符串。

| 特别字符 | 描述 |
| :------- | :--- |
| $    | 匹配输入字符串的结尾位置。如果设置了 RegExp 对象的 Multiline 属性，则 $ 也匹配 '\n' 或 '\r'。要匹配 $ 字符本身，请使用 \$。 |
| ( )  | 标记一个子表达式的开始和结束位置。子表达式可以获取供以后使用。要匹配这些字符，请使用 \( 和 \)。 |
| *    | 匹配前面的子表达式零次或多次。要匹配 * 字符，请使用 \*。     |
| +    | 匹配前面的子表达式一次或多次。要匹配 + 字符，请使用 \+。     |
| .    | 匹配除换行符 \n 之外的任何单字符。要匹配 . ，请使用 \. 。    |
| [    | 标记一个中括号表达式的开始。要匹配 [，请使用 \[。            |
| ?    | 匹配前面的子表达式零次或一次，或指明一个非贪婪限定符。要匹配 ? 字符，请使用 \?。 |
| \    | 将下一个字符标记为或特殊字符、或原义字符、或向后引用、或八进制转义符。例如， 'n' 匹配字符 'n'。'\n' 匹配换行符。序列 '\\' 匹配 "\"，而 '\(' 则匹配 "("。 |
| ^    | 匹配输入字符串的开始位置，除非在方括号表达式中使用，当该符号在方括号表达式中使用时，表示不接受该方括号表达式中的字符集合。要匹配 ^ 字符本身，请使用 \^。 |
| {    | 标记限定符表达式的开始。要匹配 {，请使用 \{。                |
| \|   | 指明两项之间的一个选择。要匹配 \|，请使用 \|。               |
| *     | 匹配前面的子表达式零次或多次。例如，zo* 能匹配 "z" 以及 "zoo"。* 等价于{0,}。 |
| +     | 匹配前面的子表达式一次或多次。例如，'zo+' 能匹配 "zo" 以及 "zoo"，但不能匹配 "z"。+ 等价于 {1,}。 |
| ?     | 匹配前面的子表达式零次或一次。例如，"do(es)?" 可以匹配 "do" 、 "does" 中的 "does" 、 "doxy" 中的 "do" 。? 等价于 {0,1}。 |
| {n}   | n 是一个非负整数。匹配确定的 n 次。例如，'o{2}' 不能匹配 "Bob" 中的 'o'，但是能匹配 "food" 中的两个 o。 |
| {n,}  | n 是一个非负整数。至少匹配n 次。例如，'o{2,}' 不能匹配 "Bob" 中的 'o'，但能匹配 "foooood" 中的所有 o。'o{1,}' 等价于 'o+'。'o{0,}' 则等价于 'o*'。 |
| {n,m} | m 和 n 均为非负整数，其中n <= m。最少匹配 n 次且最多匹配 m 次。例如，"o{1,3}" 将匹配 "fooooood" 中的前三个 o。'o{0,1}' 等价于 'o?'。请注意在逗号和两个数之间不能有空格。 |

## grep

**grep**是global search regular expression and print out the line的缩写，意思是全面搜索正则表达式并把行打印出来，是一种强大的文本搜索工具，它能使用正则表达式搜索文本，并把匹配的行打印出来。

```
命令格式：grep [选项参数] pattern file
```

- **-a 或 --text** : 不要忽略二进制的数据。
- **-A<显示行数> 或 --after-context=<显示行数>** : 除了显示符合范本样式的那一列之外，并显示该行之后的内容。
- **-b 或 --byte-offset** : 在显示符合样式的那一行之前，标示出该行第一个字符的编号。
- **-B<显示行数> 或 --before-context=<显示行数>** : 除了显示符合样式的那一行之外，并显示该行之前的内容。
- **-c 或 --count** : 计算符合样式的列数。
- **-C<显示行数> 或 --context=<显示行数>或-<显示行数>** : 除了显示符合样式的那一行之外，并显示该行之前后的内容。
- **-d <动作> 或 --directories=<动作>** : 当指定要查找的是目录而非文件时，必须使用这项参数，否则grep指令将回报信息并停止动作。
- **-e<范本样式> 或 --regexp=<范本样式>** : 指定字符串做为查找文件内容的样式。
- **-E 或 --extended-regexp** : 将样式为延伸的正则表达式来使用。
- **-f<规则文件> 或 --file=<规则文件>** : 指定规则文件，其内容含有一个或多个规则样式，让grep查找符合规则条件的文件内容，格式为每行一个规则样式。
- **-F 或 --fixed-regexp** : 将样式视为固定字符串的列表。
- **-G 或 --basic-regexp** : 将样式视为普通的表示法来使用。
- **-h 或 --no-filename** : 在显示符合样式的那一行之前，不标示该行所属的文件名称。
- **-H 或 --with-filename** : 在显示符合样式的那一行之前，表示该行所属的文件名称。
- **-i 或 --ignore-case** : 忽略字符大小写的差别。
- **-l 或 --file-with-matches** : 列出文件内容符合指定的样式的文件名称。
- **-L 或 --files-without-match** : 列出文件内容不符合指定的样式的文件名称。
- **-n 或 --line-number** : 在显示符合样式的那一行之前，标示出该行的列数编号。
- **-o 或 --only-matching** : 只显示匹配PATTERN部分。
- **-q 或 --quiet或--silent** : 不显示任何信息。
- **-r 或 --recursive** : 此参数的效果和指定"-d recurse"参数相同。
- **-s 或 --no-messages** : 不显示错误信息。
- **-v 或 --revert-match** : 显示不包含匹配文本的所有行。
- **-V 或 --version** : 显示版本信息。
- **-w 或 --word-regexp** : 只显示全字符合的列。
- **-x --line-regexp** : 只显示全列符合的列。
- **-y** : 此参数的效果和指定"-i"参数相同。

常见： -c 统计个数，-o 显示匹配的内容，-e 根据多个字符串匹配，需要多次使用，-i 不区分大小写，-n标出行号

## awk

awk是一种处理文本文件的语言，能用简短的程序处理标准输入或文件、数据排序、计算以及生成报表等等。

```
awk [选项参数] 'script' filename
或
awk [选项参数] -f scriptfile filename
```

awk 是以文件的一行内容为处理单位的。awk读取一行内容，然后根据指定条件判断是否处理此行内容，若此行文本符合条件，则按照动作处理文本，否则跳过此行文本，读取下一行进行判断。

参数： 

- -F  指定分隔符
- -f  调用脚本
- -v  定义变量

Begin{}    初始化代码块，在对每一行进行处理之前，初始化代码，主要是引用全局变量，设置FS分隔符

//            匹配代码块，可以是字符串或正则表达式

{}            命令代码块，包含一条或多条命令,多条命令用 ;  隔开

END{}      结尾代码块，在对每一行进行处理之后再执行的代码块，主要是进行最终计算或输出结尾摘要信息

**awk中字符的含义：**

* $0           表示整个当前行
* $1           每行第一个字段
* NF          **字段数量变量**
* NR          **每行的记录号，多文件记录递增**
* FNR        **与NR类似，不过多文件记录不递增，每个文件都从1开始**
* \t            制表符
* \n           换行符
* FS          **BEGIN时定义分隔符**
* RS         输入的记录分隔符， 默认为换行符(即文本是按一行一行输入)
* ~            包含
* !~           不包含
* ==         等于，必须全部相等，精确比较
* !=           不等于，精确比较
* &&         逻辑与
* ||             逻辑或
* \+ 匹配时表示1个或1个以上
* /[0-9][0-9]+/     两个或两个以上数字
* /[0-9][0-9]*/     一个或一个以上数字
* OFS         输出字段分隔符， 默认也是空格，可以改为其他的
* ORS        输出的记录分隔符，默认为换行符,即处理结果也是一行一行输出到屏幕
* -F  [:#/]    定义了三个分隔符


**print 打印**

print 是 awk打印指定内容的主要命令，也可以用 printf

* awk '{print}'  /etc/passwd   ==   awk '{print $0}'  /etc/passwd  
* awk '{print " "}'  /etc/passwd          不输出passwd的内容，而是输出相同个数的空行，进一步解释了awk是一行一行处理文本
* awk '{print "a"}'   /etc/passwd                                        输出相同个数的a行，一行只有一个a字母
* awk -F:  '{print \$1}'  /etc/passwd  ==   awk  -F  ":"  '{print $1}'  /etc/passwd
* awk -F: '{print $1 $2}'                                                 输入字段1,2，中间不分隔
* awk -F: '{print $1,$3,$6}' OFS="\t" /etc/passwd          输出字段1,3,6， 以制表符作为分隔符
* awk -F: '{print $1; print $2}'   /etc/passwd                   输入字段1,2，分行输出
* awk -F: '{print $1 "**" print $2}'   /etc/passwd                输入字段1,2，中间以**分隔
* awk -F: '{print "name:"\$1"\tid:"$3}' /etc/passwd            自定义格式输出字段1,2
* awk -F: '{print NF}' /etc/passwd                                     显示每行有多少字段
* awk -F: 'NF>2{print }' /etc/passwd                                 将每行字段数大于2的打印出来
* awk -F: 'NR==5{print}' /etc/passwd                               打印出/etc/passwd文件中的第5行
* awk -F: 'NR==5|NR==6{print}' /etc/passwd                   打印出/etc/passwd文件中的第5行和第6行
* awk -F: 'NR!=1{print}'  /etc/passwd                               不显示第一行
* awk -F: '{print > "1.txt"}'  /etc/passwd                            输出到文件中
* awk -F: '{print}' /etc/passwd > 2.txt                                使用重定向输出到文件中  



**字符匹配**

* awk  -F: '/root/{print }'  /etc/passwd                         打印出文件中含有root的行
* awk  -F: '/'$A'/{print }'  /etc/passwd                         打印出文件中含有变量 $A的行
* awk -F: '!/root/{print}' /etc/passwd                           打印出文件中不含有root的行
* awk -F:  '/root|tom/{print}'  /etc/passwd                    打印出文件中含有root或者tom的行
* awk -F: '/mail/,mysql/{print}'  test                            打印出文件中含有 mail*mysql 的行，*代表有0个或任意多个字符
* awk -F: '/^[2][7][7]*/{print}'  test                              打印出文件中以27开头的行，如27,277,27gff 等等
* awk -F: '$1~/root/{print}' /etc/passwd                     打印出文件中第一个字段是root的行
* awk -F: '($1=="root"){print}' /etc/passwd               打印出文件中第一个字段是root的行，与上面的等效
* awk -F: '$1!~/root/{print}' /etc/passwd                   打印出文件中第一个字段不是root的行
* awk -F: '($1!="root"){print}' /etc/passwd                打印出文件中第一个字段不是root的行，与上面的等效
* awk -F: '$1~/root|ftp/{print}' /etc/passwd               打印出文件中第一个字段是root或ftp的行
* awk -F: '($1=="root"||$1=="ftp"){print}' /etc/passwd   打印出文件中第一个字段是root或ftp的行，与上面的等效
* awk -F: '$1!~/root|ftp/{print}' /etc/passwd              打印出文件中第一个字段不是root或不是ftp的行
* awk -F: '($1!="root"||$1!="ftp"){print}' /etc/passwd    打印出文件中第一个字段不是root或不是ftp的行，与上面等效
* awk -F: '{if($1~/mail/) {print $1} else {print $2}}'  /etc/passwd   如果第一个字段是mail，则打印第一个字段，否则打印第2个字段

**格式化输出**

***\*awk '{printf "%-5s %.2d",$1,$2}' test\****

- printf 表示格式输出
- %格式化输出分隔符
- -8表示长度为8个字符
- s表示字符串类型，d表示小数

```
# 新建文本 awk.txt
1 hello welcome to awk
3 sdf sldkjk  slkdj jklkl lkj
6 skd wer lx sdf sada
8 sdfa afg
2 sdflk
4 sdflkak  sldkjflk  iualj sada     
```

* 打印第二列和第五列的数据

```
awk '{print $2,$5}' awk.txt
# 输出
hello awk
sdf jklkl
skd sdf
sdfa 
sdflk 
sdflkak sada
```

* 打印行数内容及行数、行字段总数

```
awk '{print "该行数据为" $0 "\t" "该行的字段总数为" NF "\t" "目前这是第几行" NR }' awk.txt 
# 输出
该行数据为1 hello welcome to awk	该行的字段总数为5	目前这是第几行1
该行数据为3 sdf sldkjk  slkdj jklkl lkj	该行的字段总数为6	目前这是第几行2
该行数据为6 skd wer lx sdf sada	该行的字段总数为6	目前这是第几行3
该行数据为8 sdfa afg	该行的字段总数为3	目前这是第几行4
该行数据为2 sdflk	该行的字段总数为2	目前这是第几行5
该行数据为4 sdflkak  sldkjflk  iualj sada	该行的字段总数为5	目前这是第几行6
```

* 设置变量

```
awk -v a=2 '{print $1,$1+a}' awk.txt
#  输出 
1 3
3 5
6 8
8 10
2 4
4 6
```

* 打印奇数行第二列

```
awk  'NR % 2 == 1 {print "行数为" NR "\t" $2} ' awk.txt
# 输出
行数为1	hello
行数为3	skd
行数为5	sdflk
```

* 使用条件语句打印第二行之后的数据

```
awk '{if(NR>1) print $2}' awk.txt
# 输出
sdf
skd
sdfa
sdflk
sdflkak
```

## sed

sed是一种流编辑器，用来过滤和替换文本。

**参数说明**：

- -e<script>或--expression=<script> 以选项中指定的script来处理输入的文本文件。
- -f<script文件>或--file=<script文件> 以选项中指定的script文件来处理输入的文本文件。
- -h或--help 显示帮助。
- -n或--quiet或--silent 仅显示script处理后的结果。
- -V或--version 显示版本信息。

**动作说明**：

- a ：新增， a 的后面可以接字串，而这些字串会在新的一行出现(目前的下一行)～
- c ：取代， c 的后面可以接字串，这些字串可以取代 n1,n2 之间的行！
- d ：删除，因为是删除啊，所以 d 后面通常不接任何咚咚；
- i ：插入， i 的后面可以接字串，而这些字串会在新的一行出现(目前的上一行)；
- p ：打印，亦即将某个选择的数据印出。通常 p 会与参数 sed -n 一起运行～
- s ：取代，可以直接进行取代的工作哩！通常这个 s 的动作可以搭配正规表示法！例如 1,20s/old/new/g 就是啦！



```
# 新建样本 sed.txt
This is my cat
  my cat's name is betty

This is my dog
  my dog's name is frank

This is my fish
  my fish's name is george

This is my goat
 my goat's name is adam
```

- 插入

```
sed -i "3i\test123" sed.txt
# 输出为
This is my cat
  my cat's name is betty
test123

This is my dog
  my dog's name is frank

This is my fish
  my fish's name is george

This is my goat
 my goat's name is adam
```

- 新增

```
sed -i "3a\test456" sed.txt
# 输出为
This is my cat
  my cat's name is betty
test123
test456

This is my dog
  my dog's name is frank

This is my fish
  my fish's name is george

This is my goat
 my goat's name is adam
```

- 替换

```
sed "s/my/hogward's/g" sed.txt
# 输出结果为
This is hogward's cat
  hogward's cat's name is betty

This is hogward's dog
  hogward's dog's name is frank

This is hogward's fish
  hogward's fish's name is george

This is hogward's goat
 hogward's goat's name is adam
```

- 输出

```
# 输出文件所有内容
sed -n '1,$p' /etc/hosts
# 将每行内容放到一行上进行展示，每行内容以逗号进行分隔。
sed ':t;N;s/\n/,/;b t' /etc/hosts
# 输出第二行到第四行之间三行的内容
sed -n "2,4p" /etc/hosts
```

- 替换

```
sed '/test123/d' sed.txt
# 输出为
This is my cat
  my cat's name is betty
test456

This is my dog
  my dog's name is frank

This is my fish
  my fish's name is george

This is my goat
 my goat's name is adam
```

