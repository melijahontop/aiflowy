package tech.aiflowy.ai.config;

import com.agentsflex.search.engine.lucene.LuceneConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AiLuceneConfig extends LuceneConfig {

    @Value("${rag.searcher.lucene.indexDirPath}")
    @Override
    public void setIndexDirPath(String indexDirPath) {
        super.setIndexDirPath(indexDirPath);
    }


}
