import java.io.File;
import java.io.IOException;
import java.util.List;


public class TextMiner
{
	private final static String DICTIONARY_FILENAME = "dict.txt";
	private final static String VECTOR_FILENAME = "vector.txt";
	private final static String RULES_FILENAME = "label-rules.txt";
//	private final static File LABELS_FILE = new File("labels.txt");
	private final static File MKDICT_OUTPUT_FILE = new File("mkdict.out");
	private final static File VECTORIZE_OUTPUT_FILE = new File("vectorize.out");
	
	public final String corpusPath;
	private final String propsPath;
	
	public TextMiner(String corpusPath, String propsPath)
	{
		this.corpusPath = corpusPath;
		this.propsPath = propsPath;
	}
	
	public void createDictionary(int dictionarySize)
	{
		try {
			new ProcessBuilder("java mkdict -cp", propsPath, ""+dictionarySize, DICTIONARY_FILENAME).redirectOutput(MKDICT_OUTPUT_FILE).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void createTermDocMatrix()
	{
		try {
			new ProcessBuilder("java vectorize -cp", propsPath, VECTOR_FILENAME).redirectOutput(VECTORIZE_OUTPUT_FILE).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void labelDocs(List<String> labels)
	{
		try {
			for (String label : labels) {
				File labelFile = new File("label-%s.txt");
				new ProcessBuilder("riktext -a", RULES_FILENAME, DICTIONARY_FILENAME, label.toUpperCase(), VECTOR_FILENAME).redirectOutput(labelFile).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
		
	
	

}
