package com.jjtest.tool.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 关键字全文检索
 */
@Component
public class KeyWordLuceneService {

    private static final String FILD_NAME = "keyWordName";


    /**
     * 索引目录
     */
    private static Directory directory;

    public List<String> searchKeyWord(String txt) {
        List<Document> search = this.search(txt);
        List<String> result = new ArrayList<>();

        for (Document document : search) {
            IndexableField field = document.getField(FILD_NAME);
            result.add(field.stringValue());
        }
        return result;
    }

    public List<Document> search(String txt) {
        List<Document> result = new ArrayList<>();
        if (StringUtils.isBlank(txt)) {
            return result;
        }
        StopWatch stopWatch = new StopWatch();
        IndexReader indexReader = null;

        try {
            indexReader = DirectoryReader.open(directory);

            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            QueryParser queryParser = new QueryParser(FILD_NAME, this.getIKAnalyzer(false));
            Query query = queryParser.parse(txt);
            TopDocs rs = indexSearcher.search(query,  10);
            for (int i = 0; i < rs.scoreDocs.length; i++) {
                Document doc = indexSearcher.doc(rs.scoreDocs[i].doc);
                result.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (indexReader != null) {
                    indexReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void initLucene() {
        Configuration instance = DefaultConfig.getInstance();
        Dictionary.initial(instance);
    }

    public void initDirectory(Map<String, String> map) {
        IndexWriter indexWriter = null;
        if (map == null || map.isEmpty()) {
            return;
        }
        List<Document> documents = new ArrayList<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            Document document = new Document();
            document.add(new StringField(FILD_NAME, entry.getValue(), Field.Store.YES));
            documents.add(document);
        }
        try {
            Directory readDirectory = new RAMDirectory();
            IndexWriterConfig config = new IndexWriterConfig(this.getIKAnalyzer(false));

            indexWriter = new IndexWriter(readDirectory, config);
            indexWriter.addDocuments(documents);
            indexWriter.commit();

            Dictionary singleton = Dictionary.getSingleton();
            singleton.addWords(map.values());
            directory = readDirectory;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (indexWriter != null) {
                try {
                    indexWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private IKAnalyzer getIKAnalyzer(boolean useSmart) {
        return new IKAnalyzer(useSmart);
    }
}
