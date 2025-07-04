import {Col, Row, Slider} from "antd";
import {MinusOutlined, PlusOutlined} from "@ant-design/icons";
import {DebouncedInput} from "../../../components/DebouncedInput";
import {useEffect, useState} from "react";
import {useDebouncedCallback} from "use-debounce";

export const LlmSlider = ({title, defaultValue = 0, step = 1, min = 0, max = 100, onChange,disabled = false}: {
    title: string,
    defaultValue?: number,
    step?: number,
    min?: number,
    max?: number
    onChange?: (value: number) => void,
    disabled?:boolean
}) => {

    const [value, setValue] = useState(defaultValue)
    useEffect(() => {
        setValue(defaultValue)
    },[defaultValue])
    const debounced = useDebouncedCallback(
        (value) => {
            onChange?.(value)
        },
        300
    );

    const changeHandler = (newValue: any) => {
        if (newValue != 0 && !newValue) newValue = 1
        setValue(newValue)
        debounced(newValue)
    };

    const plus = () => {
        let newValue = value + step;
        if (step < 1) newValue = Number(newValue.toFixed(1))
        if (newValue > max) newValue = max
        setValue(newValue)
        debounced(newValue)
    };

    const minus = () => {
        let newValue = value - step;
        if (step < 1) newValue = Number(newValue.toFixed(1))
        if (newValue < min) newValue = min
        setValue(newValue)
        debounced(newValue)
    };


    return (
        <Row style={{alignItems: "center"}}>
            <Col span={6}>{title}</Col>
            <Col span={12} style={{paddingRight: "10px"}}>
                <Slider disabled={disabled}  min={min} max={max} step={step}
                        onChange={(value) => {
                            changeHandler(value)
                        }}
                        value={value}
                />
            </Col>
            <Col span={6}>
                <DebouncedInput disabled={disabled} size="small" prefix={<MinusOutlined onClick={minus}/>}
                                suffix={<PlusOutlined onClick={plus}/>}
                                styles={{
                                    input: {textAlign: "center"},
                                    prefix: {cursor: "pointer"},
                                    suffix: {cursor: "pointer"}
                                }}
                                value={value}
                                onChange={(e) => {
                                    changeHandler(e.target.value)
                                }}
                />
            </Col>
        </Row>
    );
}