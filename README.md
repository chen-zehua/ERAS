# ERAS
ERAS工具是论文Explaining Regression via Alignment Slicing 方法的实现。该文档说明ERAS工具的使用方法、使用注意事项以及测试对象应该满足的条件等。

## 1 测试对象需满足的条件  
- 测试程序由C语言编写  
- 测试程序为单一线程  
- 测试程序可在Ubuntu12.04 32位系统上安装运行  
- 测试程序可以在命令行执行  
- 测试程序两个版本的入口文件一致  

## 2 ERAS工具使用方法及注意  
ERAS工具分为两部分：（1）测试程序预处理；（2）使用ERAS方法测试程序及结果可视化。  

由于不同程序文件组织结构及安装方法的差异，将程序安装过程集成到工具中是较为繁琐的。同时，这也并非论文方法的关注点。所以，我们将工具分为了两部分，一部分对测试程序进行预处理的安装，另一部分实现论文方法并将结果可视化。  

### 2.1 测试程序预处理  
对于被测程序的两个版本，首先需要进行源码对应，这是后面工作的基础（论文方法有提及）。所以在源程序代码中插入空行，必须不影响程序的安装和使用。  

我们提供了一个python脚本对测试程序来进行这些预处理及安装的过程。该脚本首先调用diff工具对两个版本安装程序的源码进行比较，并根据比较结果进行源码对应，然后使用以下一般Linux程序安装的过程进行安装：  
```
$ /path/to/program/configure --prefix=/path/to/installation_dir
$ make
$ make install
```
不同程序可能有差异，所以你可以查看源码注释掉安装部分。在对源码进行比较和对应之后手动安装。  

源码比较和对应会在工作目录产生`CorresFile.out`、`DeletionFile.out`、`AdditionFile.out`输出文件，他们需要作为工具下一部分的输入参数进行指定。  

### 2.2 EARS可视化工具使用  
该部分工具有GUI界面，所以你只需在启动工具后指定相关参数运行即可。  

**需要注意的是**不同程序运行操作的对象不同，所以，运行时可能会更改工作目录下的相关配置。如果你需要对测试程序进行二次运行，则需要恢复工作目录相关设置（建议测试前备份工作目录）。  

下面列出可视化工具运行测试程序时需要指定的参数：  

- 预处理部分的输出文件：`CorresFile.out`、`DeletionFile.out`、`AdditionFile.out`
- 测试程序安装成功的可执行文件目录  
- 测试程序运行参数  
- 测试程序工作目录  
- 切片分析的初始点
- 测试程序源码目录  

## 3 总结  
我们的工具只是论文方法的原型，如果需要测试更一般的程序，需要更进一步的工作。
