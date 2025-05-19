# ollama
## 什么是 ollama ？
Ollama 是一个开源工具，专注于在本地运行、部署和管理大型语言模型（LLMs）。它简化了下载、运行和与各种开源 AI 模型交互的过程，特别适合开发者和研究人员在本地环境中快速体验和测试模型。

## 主要特点：
- 本地运行：无需依赖云服务，直接在个人计算机或服务器上运行模型，保障数据隐私。

- 模型支持：支持多种开源模型（如 LLaMA、Mistral、Gemma、Phi 等），通过简单命令即可下载和切换。

- 跨平台：支持 macOS、Linux 和 Windows（需通过 WSL 或 Docker）。

- 易用性：通过命令行工具提供直观的交互方式，例如：

  - `ollama pull <模型名>`：下载模型。

  - `ollama run <模型名>`：运行模型并开始对话。

- 自定义模型：允许用户基于现有模型微调（fine-tune）并创建自己的模型变体。

## 常见用途：
- 本地开发测试：快速验证模型效果，无需云 API 费用。

- 隐私敏感场景：处理不希望上传到云端的数据（如医疗、金融信息）。

- 离线环境：在没有网络连接时仍能使用 AI 模型。

## ollama 安装
参考官网 https://ollama.com 或 github https://github.com/ollama/ollama

此处以docker安装为例
1. 拉取镜像
```
docker pull ollama/ollama:latest
```
2. 运行容器
```
docker run -d \
  --name ollama \
  -p 11434:11434 \
  -v your_data_dir:/root/.ollama \
  ollama/ollama:latest
```
3. 进入容器拉取模型
  1. 进入容器 `docker exec -it ollama bash`
  2. 拉取模型: `ollama pull <模型名称>`

4. 测试结果
```
curl "http://localhost:11434/api/generate" -d '{"model": "<模型名称>","prompt": "你好","stream": false}'
```

