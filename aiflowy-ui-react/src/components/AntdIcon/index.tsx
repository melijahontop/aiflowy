import React from "react";
import * as Icon from "@ant-design/icons";
import { IconProps } from "@ant-design/icons/es/components/IconBase";
import * as CustomIcons from '../CustomIcon/CustomIcons.tsx'; // 导入自定义图标

type Props = {
    name: string,
} & Omit<IconProps, "icon">

const AntdIcon: React.FC<Props> = (props): React.ReactNode => {
    if (!props.name) return null;

    // 检查是否是自定义图标
    if ((CustomIcons as any)[props.name]) {
        return React.createElement((CustomIcons as any)[props.name], props);
    }

    if (Icon && (Icon as any)[props.name]) {
        return React.createElement(Icon && (Icon as any)[props.name], props);
    }

};

export default AntdIcon;