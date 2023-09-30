package config

import (
	"fmt"
	"github.com/fsnotify/fsnotify"
	"github.com/sirupsen/logrus"
	"github.com/spf13/viper"
)

const DefaultConfigPath = "./config-default.yaml"

const WhitelistConfigPath = "./config-whitelist.yaml"

var ConfWhiteList = new(ConfigWhitelist)
var ConfDefault = new(ConfigDefault)

// ConfigDefault 默认配置文件的配置事项， 更改需要重新启动服务
type ConfigDefault struct {
}

// ConfigWhitelist 用于白名单注册以及服务发现
type ConfigWhitelist struct {
	WithNoAuth `mapstructure:"with_no_auth"`
}

type WithNoAuth struct {
	Params []string `mapstructure:"params"`
}

func init() {
	var defaultConfigPath string
	var whiteListConfigPath string
	// 初始化默认配置文件
	defaultConfigPath = DefaultConfigPath
	viper.SetConfigFile(defaultConfigPath)
	// 将配置文件读入 viper
	if err := viper.ReadInConfig(); err != nil {
		logrus.Errorf("failed at ReadInConfig, err: %v", err)
		panic(fmt.Sprintf("failed at init config: %v", err))
	}
	// 解析到变量中
	if err := viper.Unmarshal(&ConfDefault); err != nil {
		logrus.Errorf("failed at Unmarshal config file, err: %v", err)
		panic(fmt.Sprintf("failed at init config: %v", err))
	}
	// 返回 nil 或错误
	logrus.Infoln("global config init success...")

	// 初始化白名单配置文件
	whiteListConfigPath = WhitelistConfigPath

	viper.SetConfigFile(whiteListConfigPath)
	viper.WatchConfig()
	// 观察配置文件变动
	viper.OnConfigChange(func(in fsnotify.Event) {
		logrus.Printf("config file has changed")
		ConfWhiteList = nil
		if err := viper.Unmarshal(&ConfWhiteList); err != nil {

			logrus.Errorf("failed at unmarshal config file after change, err: %v", err)
			panic(fmt.Sprintf("failed at init config: %v", err))
		}
		logrus.Info(ConfWhiteList)
	})
	// 将配置文件读入 viper
	if err := viper.ReadInConfig(); err != nil {
		logrus.Errorf("failed at ReadInConfig, err: %v", err)
		panic(fmt.Sprintf("failed at init config: %v", err))
	}
	// 解析到变量中
	if err := viper.Unmarshal(&ConfWhiteList); err != nil {
		logrus.Errorf("failed at Unmarshal config file, err: %v", err)
		panic(fmt.Sprintf("failed at init config: %v", err))
	}
	// 如果有环境变量就覆盖，适用于本地开发使用文件，实际运行使用环境变量的场景
	//DBHost := os.Getenv("CEPHALON_DB_HOST")
	//if DBHost != "" {
	//	Conf.DBConfig.Host = DBHost
	//}

	// 返回 nil 或错误
	logrus.Infoln("global config init success...")
}
