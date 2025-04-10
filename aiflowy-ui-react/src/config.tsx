import {ThemeConfig} from "antd";

import zhCN from 'antd/locale/zh_CN';

export const appConfig = {

    // 默认语言
    locale: zhCN,

    // 主题配色
    theme: {

        //全局配置
        token: {
            // 主色调
            // colorPrimary: '#152b39',
            // colorPrimaryText:"#fff",
        },

        //组件配置
        components: {
            Menu: {
                //icon 大小
                iconSize: 16,
            },
            Segmented: {
                trackPadding: 4
            },
        },
    } as ThemeConfig,

    // 布局方式
    layout: {
        menuPosition: "left",
        contentWidth: "Fixed",
        fixedHeader: false,
        fixedSliderBar: false,
        hideHintAlert: false,
        hideCopyButton: false
    }
}