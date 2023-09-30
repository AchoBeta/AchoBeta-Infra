# AchoBeta-infra
achobeta team infrastructure service

# before develop

1. execute command in the root directory
```shell
git config core.hooksPath .githooks 
chmod -R -x .githooks 
```

The purpose of the above command is to trigger hook to automatically delete the custom branch after the PR merge after submitting the code

# commit message format

```bash
<type>(<scope>): <subject>
```

## type

feat: feature

fix: fix bug

docs: documentation

test: add test function

## subject

No period or punctuation at the end

e.g.
```bash
feat: add new feature
fix: fix a bug
```
