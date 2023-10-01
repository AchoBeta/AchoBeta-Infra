# AchoBeta-infra
AchoBeta 团队基础建设服务

# 开发之前

1. 请在根目录执行下面的命令

```shell
git config core.hooksPath .githooks 
chmod -R -x .githooks 
```

上述命令的目的是在 git 提交时触发钩子以确认提交消息格式是否正确。

2.**请阅读下面的开发规范**

# 分支命名约定
必须确保:

1. 分支名应当携带上开发者名称以便标识

2. 分支名必须清楚的表述该分支解决了什么问题

分支命名必须标准化, 参照该格式进行分支命名
```bash
<type>-<name>-<description>
```
举个例子:
- 如果它是开发新功能的分支，则命名约定如下
```bash
feature-<name>-<feature description>
e.g.: feature-jett-dev_log_system
```

- 如果他是为了修复 bug 而开辟的分支:
```bash
bugfix-<name>-<bug name>
e.g.: bugfix-jett-login_error
```
其他分支功能类型如下:
`hotfix`、`release`...


# 提交信息规范
提交信息应尽可能清晰，且每个提交只实现一个小功能以达到细粒度。

```bash
<type>(<scope>): <subject>

e.g.: feat: add new api
or: feat(common): add new api
```

## type

feat: feature（新功能）

fix: fix bug（修复 bug）

docs: documentation（文档）

test: add test function（增添测试函数）

style: format (不影响代码操作的更改).

refactor: Refactoring (重构，它并不是开发新功能或修复 bug).

## subject

一个句子结尾请不要使用任何标点符号修饰

e.g.
```bash
feat: add new feature
fix: fix a bug
```
