```shell
…or create a new repository on the command line
echo "# test-data-jdbctemplate" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin git@github.com:xconfigurator/test-data-jdbctemplate.git
git push -u origin main

…or push an existing repository from the command line
git remote add origin git@github.com:xconfigurator/test-data-jdbctemplate.git
git branch -M main
git push -u origin main
…or import code from another repository
You can initialize this repository with code from a Subversion, Mercurial, or TFS project.
```

## 环境准备
Spring Boot能够自动执行DDL, DML。
- schema.sql
- data.sql
脚本在类路径上自动加载。
```shell
# 如需自动加载需要开启。默认是never
spring.sql.init.mode=always
```