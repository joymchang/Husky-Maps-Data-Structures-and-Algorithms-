import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import minpq.DoubleMapMinPQ;

/**
 * Display the most commonly-reported WCAG recommendations.
 */
public class ReportAnalyzer {
    public static void main(String[] args) throws IOException {
        File inputFile = new File("data/wcag.tsv");
        Map<String, String> wcagDefinitions = new LinkedHashMap<>();
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("\t", 2);
            String index = "wcag" + line[0].replace(".", "");
            String title = line[1];
            wcagDefinitions.put(index, title);
        }

        Pattern re = Pattern.compile("wcag\\d{3,4}");
        List<String> wcagTags = Files.walk(Paths.get("data/reports"))
                .map(path -> {
                    try {
                        return Files.readString(path);
                    } catch (IOException e) {
                        return "";
                    }
                })
                .flatMap(contents -> re.matcher(contents).results())
                .map(MatchResult::group)
                .toList();

        // TODO: Display the most commonly-reported WCAG recommendations using MinPQ
        DoubleMapMinPQ<String> pq = new DoubleMapMinPQ<>();
        for(String tag : wcagTags) {
            if(pq.contains(tag)){
                double oldCount = pq.getPriority(tag);
                pq.changePriority(tag, oldCount - 1);
            } else {
                pq.add(tag, -1);
            }
        }

        System.out.println("Top 3 most commonly report WCAG tags:");
        for(int i = 0; i < 3 && !pq.isEmpty(); i++){
            String tag = pq.removeMin();
            String desc = wcagDefinitions.get(tag);
            System.out.println((i + 1) + ". " + desc + " - " + tag);
        }

        throw new UnsupportedOperationException();
    }
}