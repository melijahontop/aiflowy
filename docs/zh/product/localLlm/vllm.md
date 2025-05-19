# 什么是 vLlm ?
vLlm（Virtual Large Language Model）是一个开源的高吞吐、低延迟的 LLM 推理与服务框架，由伯克利大学 Sky Computing Lab 首创，后逐步发展为社区驱动的项目
。它旨在简化大规模语言模型（如 LLaMA、Mistral 等）在生产环境中的部署与调用，使开发者能够以更低成本、更高效率地提供实时 AI 服务.

## 核心特性
### PagedAttention 内存管理
借鉴操作系统的虚拟内存分页思想，PagedAttention 可将注意力键值对（KV cache）存储在非连续内存页中，大幅减少碎片和冗余复制，实现几乎零浪费的内存利用率.

### 连续批处理（Continuous Batching）
对用户请求进行动态聚合，持续填充 GPU 计算资源，显著提升吞吐量，相比传统推理引擎可多倍提高令牌生成速度.

### CUDA/HIP 图加速与优化内核
内置对 FlashAttention、FlashInfer 等高性能 CUDA 内核的集成，并通过图执行（CUDA/HIP Graph）机制减少调度开销，实现更快的推理速度.

### 多种量化支持
支持 GPTQ、AWQ、INT4、INT8 乃至最新的 FP8 量化格式，在保证精度的同时进一步降低显存占用与算力需求.

### 投机解码（Speculative Decoding）与 Chunked Prefill
通过在高置信度路径上预先生成若干候选令牌，加速解码过程；同时对预填充阶段进行分块优化，缩短启动延迟.

## 架构与工作流程
### 多进程 GPU 调度
每个 GPU 对应一个独立 Worker 进程，主进程（driver worker）负责调度、负载均衡与缓存管理，其它 Worker 专注于模型执行，确保多卡并行时的高效通信与协作.

### 动态 KV Cache 共享
在同一会话内或跨会话请求之间灵活共享 KV cache，减少重复计算与内存占用，实现更大的批量服务能力.

### OpenAI API 兼容接口
提供与 OpenAI 完全兼容的 HTTP 接口，支持直接替换现有调用 OpenAI 的应用，无需修改客户端代码即可切换到 vLlm 服务



## vLlm 安装
参考 vLlm 官网文档 https://docs.vllm.ai 或 GitHub https://github.com/vLlm-project/vLlm

docker安装示例：
```
docker run --runtime nvidia --gpus all \
    -v ~/.cache/huggingface:/root/.cache/huggingface \
    --env "HUGGING_FACE_HUB_TOKEN=<secret>" \
    -p 8000:8000 \
    --ipc=host \
    vLlm/vLlm-openai:latest \
    --model mistralai/Mistral-7B-v0.1
```
> vLlm 默认从 [Hugging Face](https://huggingface.co/) 下载模型