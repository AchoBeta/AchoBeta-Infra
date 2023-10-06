# AchoBeta-infra
achobeta team infrastructure service

# before develop

1. execute command in the root directory

```shell
git config core.hooksPath .githooks 
chmod -R -x .githooks 
```
+ The purpose of the above command is to trigger a hook when submitting a commit to confirm whether the commit message is correct.

If you want to deploy the ELK service component via docker, please see the following content
```shell
chmod -R 777 /docker/elk/elasticsearch/data /docker/elk/elasticsearch/logs
```
+ For data/logs directory, please execute the above command to give reading and writing permissions, otherwise ES will not be written into the data

3. **Read the development specifications below**

# branch naming convention
we must confirm:

1. Branch naming should include a name to identify the person responsible.

2. Branch naming must clearly express what problem the branch is working on.

so branch naming must be standardizedSo branch naming must be standardized.
```bash
<type>-<name>-<description>
```
for example:
- if it is a branch to develop new functions, the naming convention is as follows
```bash
feature-<name>-<feature description>
e.g.: feature-jett-dev_log_system
```

- if is fix bugs:
```bash
bugfix-<name>-<bug name>
e.g.: bugfix-jett-login_error
```
and other types:
`hotfix`、`release`...


# commit message format
commit message should be written as clearly as possible, and each commit should only do one thing.

```bash
<type>(<scope>): <subject>

e.g.: feat: add new api
or: feat(common): add new api
```

## type

feat: feature

fix: fix bug

docs: documentation

test: add test function

style: format (changes that do not affect code operation).

refactor: Refactoring (that is, code changes that are not new features or bug fixes).

## subject

No period or punctuation at the end

e.g.
```bash
feat: add new feature
fix: fix a bug
```
