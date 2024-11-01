<h1 style="text-align: center">ELADMIN Admin management system</h1>

#### INTRODUCTION
A backend management system with front-end and back-end separation based on Spring Boot 2.6.4, Mybatis-Plus, JWT, Spring Security, Redis, and Vue

**Development Documentation：**  [https://eladmin.vip](https://eladmin.vip)

**ACCOUNT AND PASSWORD：** `admin / 123456`

#### Project source code

|        | Backend source code                                 | Frontend source code                     |
|--------|--------------------------------------|------------------------------------------|
| github | https://github.com/elunez/eladmin-mp | https://github.com/elunez/eladmin-web-mp |
| GITEE  | https://gitee.com/elunez/eladmin-mp     | https://gitee.com/elunez/eladmin-web-mp  |


#### Main features
- Use the latest technology stack and rich community resources.
- High-efficiency development, the code generator can generate front-end and back-end codes with one click
- Support data dictionary, which can easily manage some states
- Support interface current limiting to avoid malicious requests causing excessive pressure on the service layer
- Support interface-level functional permissions and data permissions, and customize operations
- Customized permission annotations and anonymous interface annotations, which can quickly intercept and release interfaces
- Encapsulate some commonly used front-end components: table data requests, data dictionaries, etc.
- Unified exception interception and processing for the front and back ends, unified output of exceptions, avoiding tedious judgments
- Support online user management and server performance monitoring, support limiting single user login
- Support operation and maintenance management, which can easily deploy and manage applications on remote servers

####  System functions
- User management: Provide user configuration. After adding a new user, the default password is 123456
- Role management: Assign permissions and menus, and set data permissions for roles according to departments
- Menu management: Dynamic menu routing has been implemented, the backend is configurable, and multi-level menus are supported
- Department management: Configurable system organizational structure, tree table display
- Position management: Configure positions in each department
- Dictionary management: Can maintain some commonly used fixed data, such as: status, gender, etc.
- System log: Record user operation logs and exception logs to facilitate developers to locate and troubleshoot
- SQL monitoring: Use druid to monitor database access performance, the default username is admin, and the password is 123456
- Scheduled tasks: Integrate Quartz to do scheduled tasks, add task logs, and the task operation status is clear at a glance
- Code generation: Highly flexible generation of front-end and back-end codes to reduce a large number of repetitive tasks
- Mail tool: Use rich text to send html format emails
- Qiniu Cloud Storage: Synchronize Qiniu Cloud storage data to the system, and operate cloud data directly without logging into Qiniu Cloud
- Alipay Payment: Alipay payment is integrated and a test account is provided for self-testing
- Service Monitoring: Monitor server load
- Operation and Maintenance Management: Deploy your application with one click

#### Project structure
The project adopts a development method of dividing modules by function, and the structure is as follows

- `eladmin-common` is the common module of the system, and various tool classes and public configurations exist in this module

- `eladmin-system` is the core module of the system and the project entry module, and is also the module that needs to be packaged and deployed in the end

- `eladmin-logging` is the log module of the system. Other modules need to introduce this module if they need to record logs

- `eladmin-tools` is a third-party tool module, including: email, Qiniu cloud storage, local storage, Alipay

- `eladmin-generator` is the code generation module of the system, which supports the generation of front-end and back-end CRUD codes

#### Detailed structure

```
- eladmin-common Common module
- annotation is a custom annotation for the system
- aspect is a custom annotation aspect
- base provides the Entity base class
- config is a custom permission implementation, redis configuration, swagger configuration, Rsa configuration, etc.
- exception is a unified exception handling for the project
- utils is a system general tool class
- eladmin-system is a system core module (system startup entry)
- config configures cross-domain and static resources, and data permissions
- thread is related to thread pools
- modules are system-related modules (login authorization, system monitoring, scheduled tasks, operation and maintenance management, etc.)
- eladmin-logging is a system log module
- eladmin-tools is a system third-party tool module
- eladmin-generator is a system code generation module
```

