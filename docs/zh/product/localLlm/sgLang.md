# 什么是 SGLang ?
SGLang 是一种专为 大语言模型 (LLM) 服务开发的 推理编排语言，主要由 Skywork AI 开发。它的设计目标是 简化和优化大语言模型的推理流程，包括提示模板的编写、多轮对话、工具调用（Tool Calling）、API 集成等任务。

## SGLang 的核心特点
- 类脚本语言的语法风格

   -  类似 Python 的语法，易读易写，支持变量、条件语句、循环、函数调用等编程结构。

    - 可以方便地定义复杂的推理逻辑流程。

- Prompt 模板 + 控制逻辑的融合

  - 不只是静态 Prompt，还能在一个脚本中动态生成 Prompt、处理模型响应，并根据结果做决策。

  - 例如：可以根据用户输入的意图调用不同的工具或触发不同的逻辑分支。

- 原生支持多轮对话和上下文管理

  - 非常适合构建 Chatbot、多轮问答系统。

  - 支持 conversation memory 的机制，自动处理对话上下文。

- 工具（Tool）调用

  - 支持调用外部函数、API，或者通过插件接入数据库、搜索引擎、计算模块等。

  - 可用于 Agent 系统开发。

- 易于部署和集成

  - 可以作为服务部署，结合 Web 前端或 API 接口。

  - 支持 OpenAI、Skywork、LLaMA 等主流模型。

## SGLang 安装
参考vllm官网文档 https://docs.sglang.ai/index.html 或 GitHub https://github.com/sgl-project/sglang

docker安装示例：
```
docker run --gpus all \
    --shm-size 32g \
    -p 30000:30000 \
    -v ~/.cache/huggingface:/root/.cache/huggingface \
    --env "HF_TOKEN=<secret>" \
    --ipc=host \
    lmsysorg/sglang:latest \
    python3 -m sglang.launch_server --model-path meta-llama/Llama-3.1-8B-Instruct --host 0.0.0.0 --port 30000
```
> SGLang 默认从 [Hugging Face](https://huggingface.co/) 下载模型
