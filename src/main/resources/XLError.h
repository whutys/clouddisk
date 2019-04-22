#pragma once

#define XL_SUCCESS                     0
#define XL_ERROR_FAIL                  0x10000000

//尚未进行初始化
#define XL_ERROR_UNINITAILIZE          XL_ERROR_FAIL+1

// 不支持的协议，目前只支持HTTP和FTP
#define XL_ERROR_UNSPORTED_PROTOCOL    XL_ERROR_FAIL+2

// 初始化托盘图标失败
#define XL_ERROR_INIT_TASK_TRAY_ICON_FAIL  XL_ERROR_FAIL+3

// 添加托盘图标失败
#define XL_ERROR_ADD_TASK_TRAY_ICON_FAIL   XL_ERROR_FAIL+4

// 指针为空
#define XL_ERROR_POINTER_IS_NULL    XL_ERROR_FAIL+5

// 字符串是空串
#define XL_ERROR_STRING_IS_EMPTY    XL_ERROR_FAIL+6

// 传入的路径没有包含文件名
#define XL_ERROR_PATH_DONT_INCLUDE_FILENAME    XL_ERROR_FAIL+7

// 创建目录失败
#define XL_ERROR_CREATE_DIRECTORY_FAIL    XL_ERROR_FAIL+8

// 内存不足
#define XL_ERROR_MEMORY_ISNT_ENOUGH    XL_ERROR_FAIL+9

// 参数不合法
#define XL_ERROR_INVALID_ARG    XL_ERROR_FAIL+10

// 任务不存在
#define XL_ERROR_TASK_DONT_EXIST    XL_ERROR_FAIL+11

// 文件名不合法
#define XL_ERROR_FILE_NAME_INVALID   XL_ERROR_FAIL+12

// 没有实现
#define XL_ERROR_NOTIMPL    XL_ERROR_FAIL+13

// 已经创建的任务数达到最大任务数，无法继续创建任务
#define XL_ERROR_TASKNUM_EXCEED_MAXNUM    XL_ERROR_FAIL+14