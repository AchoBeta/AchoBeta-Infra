# AchoBeta-infra
AchoBeta 团队基础建设服务

# 开发之前

1. 请在根目录执行下面的命令

```shell
git config core.hooksPath .githooks 
chmod -R -x .githooks 
```

+ 上述命令的目的是在 git 提交时触发钩子以确认提交消息格式是否正确。

如果您想要通过 docker 部署 elk 服务组件，请看下面内容

```shell
chmod -R 777 /docker/elk/elasticsearch/data /docker/elk/elasticsearch/logs
```

+ 数据/日志目录需要执行上述命令, 否则 es 无法读取数据文件

2. **请阅读下面的开发规范**

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

```text
# 主要type
feat:     增加新功能
fix:      修复 bug

# 特殊type
docs:     只改动了文档相关的内容
style:    不影响代码含义的改动，例如去掉空格、改变缩进、增删分号
build:    构造工具的或者外部依赖的改动，例如 webpack，npm
refactor: 代码重构时使用
revert:   执行 git revert 打印的 message

# 暂不使用type
test:     添加测试或者修改现有测试
perf:     提高性能的改动
ci:       与 CI（持续集成服务）有关的改动
chore:    不修改 src 或者 test 的其余修改，例如构建过程或辅助工具的变动
```

## subject

一个句子结尾请不要使用任何标点符号修饰

e.g.
```bash
feat: add new feature
fix: fix a bug
```
