package middleware

import (
	"bytes"
	"github.com/gin-gonic/gin"
	"github.com/sirupsen/logrus"
	"io"
)

func GetRequestInfo(context *gin.Context) {
	// 记录调用方 IP 地址
	ip := context.ClientIP()

	// 记录请求的路径和方法
	path := context.Request.URL.Path

	method := context.Request.Method

	// 读取请求头
	headers := context.Request.Header

	// 读取请求体
	body, _ := io.ReadAll(context.Request.Body)
	// 将请求体内容重新放入请求体中，以便后续处理程序读取
	context.Request.Body = io.NopCloser(bytes.NewBuffer(body))

	logrus.Infof("IP: %v; PATH: %v %v; HEADERS: %+v; BODY: %v;\n", ip, method, path, headers, body)

	context.Next()
}
