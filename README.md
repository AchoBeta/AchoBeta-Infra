# AchoBeta-infra
achobeta team infrastructure service

# before develop

1. execute command in the root directory
```shell
git config core.hooksPath .githooks 
chmod -R -x .githooks 
```

以上命令的作用是在于提交代码后 pr 合并会触发 hook 自动删除自定义分支

# commit message 格式

```bash
<type>(<scope>): <subject>
```

## type

feat：新功能（feature）

fix：修复bug，可以是QA发现的BUG，也可以是研发自己发现的BUG

docs：文档（documentation）

test：增加测试

## subject

1. 结尾不加句号或其他标点符号
2. 中英文之间需要加空格

例如:
```bash
feat: add new feature
fix: fix a bug
test: 实现 Service 层单元测试
```
