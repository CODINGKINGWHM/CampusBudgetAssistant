# 校园消费预算助手

> 《金融系统应用开发与实践》课程大作业  
> 班级：计算机242　｜　学号：2406100226　｜　姓名：王浩铭

校园消费预算助手是一款面向大学生日常消费场景开发的 Android 应用。项目围绕“预算设置—收支记录—消费分类—结果统计”完成业务闭环，帮助用户直观掌握本月预算、累计支出、剩余预算以及必要消费占比。

## 项目说明

本项目并非一次性上传成品，而是使用 Git 按功能模块持续开发：从工程初始化、页面布局和 Intent 传值，到控件交互、适配器、登录验证、业务逻辑优化和统计功能，均保留了可追溯的提交记录。

- 开发工具：Android Studio
- 开发语言：Java
- 应用包名：`com.wanghaoming.campusbudgetassistant`
- 最低系统版本：Android 7.0（API 24）
- 目标 SDK：API 36
- 当前版本：1.0

## 主要功能

1. **密码登录**：使用学号后四位进行访问验证，并对空值和错误密码进行提示。
2. **预算设置**：输入并设置本月预算，自动计算累计支出和剩余金额。
3. **收支记录**：支持新增支出与收入，金额必填、说明选填。
4. **支出分类**：通过下拉列表选择餐饮、交通、学习、购物、娱乐或其他类别。
5. **必要消费标记**：支出可标记为必要消费，首页自动计算必要消费金额及其占总支出的比例。
6. **收入计入预算**：收入可选择是否计入本月预算，计入后预算金额实时更新。
7. **记录列表**：使用 ListView 展示收支记录，点击列表项可查看记录详情。

## 课程知识点应用

项目包含 `LoginActivity`、`MainActivity` 和 `AddRecordActivity` 三个 Activity，并完成多次页面跳转及 Intent 数据传递。

| 知识点/控件 | 项目中的用途 |
| --- | --- |
| TextView | 标题、身份信息、预算汇总和选择结果展示 |
| EditText | 密码、预算、金额和说明输入 |
| Button | 登录、设置预算、添加、保存和取消操作 |
| RadioGroup / RadioButton | 选择“支出”或“收入” |
| Spinner | 选择消费类别 |
| CheckBox | 标记必要消费、选择收入是否计入预算 |
| ListView | 展示消费记录 |
| SimpleAdapter | 将记录数据绑定至 ListView |
| Intent | Activity 页面跳转和双向数据传递 |
| Toast | 输入校验和操作结果提示 |

## 页面与业务流程

```text
登录页面
   │ 密码验证通过
   ▼
预算管理首页 ──设置预算──> 更新预算汇总
   │
   └──添加记录──> 添加消费记录页面
                       │
                       ├─ 支出：类别 + 必要消费标记
                       └─ 收入：是否计入本月预算
                       │
                       └──保存并通过 Intent 返回首页
```

## Git 迭代记录

仓库保留了从初始化到最终交付的完整开发过程，可点击提交编号查看每次代码变化。

| 日期 | 提交 | 开发内容 |
| --- | --- | --- |
| 2026-10-29 | [`329dfdc`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/329dfdc) | 初始化校园消费预算助手 Android 工程 |
| 2026-10-29 | [`80dca58`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/80dca58) | 完成主页面预算设置与身份信息展示 |
| 2026-10-29 | [`2e60539`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/2e60539) | 添加记录页面并实现 Intent 双向传值 |
| 2026-10-29 | [`04bb78b`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/04bb78b) | 加入 Spinner、单选框和复选框交互 |
| 2026-10-29 | [`b0b97c5`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/b0b97c5) | 使用 SimpleAdapter 展示消费记录列表 |
| 2026-10-29 | [`441e14e`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/441e14e) | 修复深色模式下输入和选项文字不可见问题 |
| 2026-10-29 | [`6e0d8a6`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/6e0d8a6) | 增加密码登录页面和访问验证 |
| 2026-11-01 | [`5318986`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/5318986) | 优化登录提示和收支预算逻辑 |
| 2026-11-01 | [`0f7e92d`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/0f7e92d) | 优化收入记录的类别选择逻辑 |
| 2026-11-01 | [`96e0759`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/96e0759) | 优化登录页面信息展示 |
| 2026-11-01 | [`4526efe`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/4526efe) | 增加必要消费金额和占比统计 |
| 2026-11-01 | [`8c32f45`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/8c32f45) | 提交课程作业完整材料 |
| 2026-11-01 | [`9e2e27b`](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commit/9e2e27b) | 规范课程作业文件夹名称 |

查看[全部提交记录](https://github.com/CODINGKINGWHM/CampusBudgetAssistant/commits/main/)。

## 项目结构

```text
CampusBudgetAssistant/
├─ app/src/main/java/.../
│  ├─ LoginActivity.java
│  ├─ MainActivity.java
│  └─ AddRecordActivity.java
├─ app/src/main/res/
│  ├─ layout/                 页面及列表项布局
│  └─ values/                 字符串、颜色、主题和选项数据
├─ gradle/                    Gradle Wrapper 配置
└─ 王浩铭金融软件与开发课程大作业/
   └─ 计算机242-2406100226-王浩铭.zip
```

## 构建与运行

1. 使用 Android Studio 打开本仓库根目录。
2. 将 Gradle JVM 设置为 JDK 21。
3. 等待 Gradle 同步完成。
4. 连接 Android 7.0 及以上设备，选择 `app` 配置后运行。
5. 登录密码为学号后四位：`0226`。

## 作业材料

完整压缩包包含：

- Android Studio 完整源码工程
- 可运行 APK
- Word 版设计开发文档
- 功能演示与讲解视频

➡️ [下载完整课程作业压缩包](./王浩铭金融软件与开发课程大作业/计算机242-2406100226-王浩铭.zip)

---

本项目为《金融系统应用开发与实践》课程学习成果，由王浩铭提交，并保留完整的设计、编码、调试与文档整理记录。
