import luence.Item;
import luence.ItemProvider;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public class main {
    private static void addDoc(IndexWriter w, int id, float price, String name, String category, String description)
            throws IOException {
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(id), Field.Store.YES));
        doc.add(new StringField("price", String.valueOf(price), Field.Store.YES));
        doc.add(new StringField("name", name, Field.Store.YES));
        doc.add(new StringField("cateogry", category, Field.Store.YES));
        doc.add(new TextField("description", description, Field.Store.YES));
        w.addDocument(doc);
    }

    public static void main(String[] args) {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory index = null;
        try {
            File pat = new File("C:\\Users\\Szymon\\Desktop\\Semestr5\\PP\\Luence\\src\\main\\java\\luence\\index");
            index = new SimpleFSDirectory(pat.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try (ItemProvider provider = new ItemProvider("C:\\Users\\Szymon\\Desktop\\Semestr5\\PP\\Luence\\src\\main\\java\\luence\\items.xml")) {
            assert index != null;
            IndexWriter w = new IndexWriter(index, config);
            while (provider.hasNext()) {
                Item item = provider.next();
                try {
                    addDoc(w, item.getId(), item.getPrice(), item.getName(), item.getCategory(), item.getDescription());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            w.close();
        } catch (XMLStreamException | IOException ex) {
            ex.printStackTrace();
        }

        try {
            Query q = new QueryParser("name", analyzer).parse("zestaw~");

            int hitsPerPage = 10;
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            System.out.println("Found " + hits.length + " hits.");
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                System.out.println((i+1) + ". " + d.get("id") + "\t" + d.get("name"));
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }




    }
}
