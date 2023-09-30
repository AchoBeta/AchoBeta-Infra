package router

import "github.com/gin-gonic/gin"

func SetupRouter() *gin.Engine {
	r := gin.Default()
	return r
}
