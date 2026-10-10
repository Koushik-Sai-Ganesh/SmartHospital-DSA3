import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * SmartHospital: educational Advanced DSA project.
 * Uses CSV persistence for patient records. All patient data is fictional.
 * Algorithms are implemented for learning/demo purposes, not clinical decisions.
 */
public class SmartHospital {
    // Resolve one canonical project data folder, even if launched from the src folder.
    private static final Path PROJECT_ROOT = resolveProjectRoot();
    private static final Path DATA_DIR_PATH = PROJECT_ROOT.resolve("data");
    private static final Path PATIENTS_PATH = DATA_DIR_PATH.resolve("patients.csv");
    private static final Path WIKI_PATH = DATA_DIR_PATH.resolve("text_corpus.txt");
    private static final String PATIENTS_FILE = PATIENTS_PATH.toAbsolutePath().toString();

    private static Path resolveProjectRoot() {
        Path cwd = Paths.get("").toAbsolutePath().normalize();
        // If launched from .../SmartHospital-FullProject/src, always use its parent.
        if (cwd.getFileName() != null && cwd.getFileName().toString().equalsIgnoreCase("src")
                && cwd.getParent() != null
                && Files.isDirectory(cwd.getParent().resolve("data"))) {
            return cwd.getParent();
        }
        // Find the project folder when launched from the project root or a nested folder.
        Path p = cwd;
        for (int i = 0; i < 5 && p != null; i++, p = p.getParent()) {
            if (Files.isDirectory(p.resolve("src")) && Files.isDirectory(p.resolve("data"))) {
                return p;
            }
        }
        // Fallback: use the current directory consistently.
        return cwd;
    }
    private static final BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));

    static class Patient {
        String id, name, age, department, history;
        Patient(String id, String name, String age, String department, String history) {
            this.id=id; this.name=name; this.age=age; this.department=department; this.history=history;
        }
    }

    public static void main(String[] args) {
        try {
            Files.createDirectories(DATA_DIR_PATH);
            ensureFiles();
            System.out.println("============================================================");
            System.out.println(" SMART HOSPITAL | Advanced Data Structures & Algorithms");
            System.out.println(" Academic prototype • fictional data • not a clinical tool");
            System.out.println("============================================================");
            while (true) {
                printMenu();
                int choice = readInt("Choose an option: ");
                try {
                    switch (choice) {
                        case 1: viewPatients(); break;
                        case 2: addPatient(); break;
                        case 3: searchPatient(); break;
                        case 4: deletePatient(); break;
                        case 5: textHackQueries(); break;
                        case 6: stringAlgorithmsMenu(); break;
                        case 7: suffixStructures(); break;
                        case 8: dynamicProgrammingMenu(); break;
                        case 9: networkFlowMenu(); break;
                        case 10: npCompletenessMenu(); break;
                        case 11: randomizedParallelMenu(); break;
                        case 12: runAllDemo(); break;
                        case 0: System.out.println("Goodbye. Patient records remain saved in " + PATIENTS_FILE); return;
                        default: System.out.println("Please choose a listed option.");
                    }
                } catch (Exception e) {
                    System.out.println("Could not complete that operation: " + e.getMessage());
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Startup error: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\n---------------------- MAIN MENU ---------------------------");
        System.out.println("PATIENT DATA (CSV)");
        System.out.println(" 1. View patient records");
        System.out.println(" 2. Add patient record (saved to CSV)");
        System.out.println(" 3. Search patient by ID/name");
        System.out.println(" 4. Delete patient by ID (updates CSV)");
        System.out.println("MODULE 1 — TEXTHACK QUERY ENGINE");
        System.out.println(" 5. TextHack queries (search, fuzzy match, similarity, citation flow, scheduling, prime test)");
        System.out.println("MODULE 2 — STRING ALGORITHMS");
        System.out.println(" 6. String algorithms (Naive, KMP, Z, Rabin-Karp, Aho-Corasick, Trie)");
        System.out.println(" 7. Suffix structures (Suffix Array, Kasai LCP; suffix tree/automaton preview)");
        System.out.println("MODULE 3 — ADVANCED DYNAMIC PROGRAMMING");
        System.out.println(" 8. DP lab (edit distance, sequence alignment, interval, bitmask/TSP, tree-DP, SOS)");
        System.out.println("MODULE 4 — NETWORK FLOW");
        System.out.println(" 9. Flow lab (Ford-Fulkerson, Edmonds-Karp, Dinic, matching, min-cut concepts)");
        System.out.println("MODULE 5 — NP-COMPLETENESS & APPROXIMATION");
        System.out.println("10. Complexity/reductions/Vertex-Cover 2-approximation");
        System.out.println("MODULE 6 — RANDOMIZED & PARALLEL ALGORITHMS");
        System.out.println("11. Randomized QuickSort, Miller-Rabin, reservoir sampling, reduce/scan");
        System.out.println("12. Run automatic demonstration / smoke tests");
        System.out.println(" 0. Exit");
        System.out.println("------------------------------------------------------------");
    }

    private static void ensureFiles() throws IOException {
        Path p = PATIENTS_PATH;
        if (!Files.exists(p)) {
            String sample = "id,name,age,department,history\n" +
                "101,Ananya,45,Cardiology,Patient has hypertension and diabetes.\n" +
                "102,Rahul,31,Neurology,Patient reports migraine episodes and fever.\n" +
                "103,Meera,56,Pulmonology,History of asthma and pneumonia.\n";
            Files.write(p, sample.getBytes(StandardCharsets.UTF_8));
        }
        Path w = WIKI_PATH;
        if (!Files.exists(w)) {
            String corpus = "India is a country in South Asia. New Delhi is the capital of India.\n" +
                "The Indian Wikipedia is a collection of articles in Indian languages.\n" +
                "Algorithms study efficient methods for solving computational problems.\n" +
                "Hospitals maintain patient records, reports, laboratory data, and appointments.\n" +
                "A graph consists of vertices and edges. Maximum flow studies capacity in a network.\n" +
                "Dynamic programming solves problems with overlapping subproblems and optimal substructure.\n" +
                "भारत दक्षिण एशिया में स्थित एक देश है। नई दिल्ली भारत की राजधानी है।\n" +
                "ఆరోగ్యం మరియు ఆసుపత్రి సేవలు సమాజానికి ముఖ్యమైనవి.\n";
            Files.write(w, corpus.getBytes(StandardCharsets.UTF_8));
        }
    }

    private static String readLine(String prompt) throws IOException {
        System.out.print(prompt);
        String s = IN.readLine();
        return s == null ? "" : s.trim();
    }
    private static int readInt(String prompt) throws IOException {
        while (true) {
            String s = readLine(prompt);
            try { return Integer.parseInt(s); } catch (NumberFormatException e) { System.out.println("Enter a whole number."); }
        }
    }
    private static long readLong(String prompt) throws IOException {
        while (true) {
            String s = readLine(prompt);
            try { return Long.parseLong(s); } catch (NumberFormatException e) { System.out.println("Enter an integer."); }
        }
    }
    private static String csvEscape(String s) {
        if (s.indexOf(',') >= 0 || s.indexOf('"') >= 0 || s.indexOf('\n') >= 0) return "\"" + s.replace("\"", "\"\"") + "\"";
        return s;
    }
    private static String[] parseCsvLine(String line) {
        String[] fields = new String[5]; int f=0; boolean quoted=false; StringBuilder b=new StringBuilder();
        for (int i=0;i<line.length();i++) {
            char c=line.charAt(i);
            if (c=='"') {
                if (quoted && i+1<line.length() && line.charAt(i+1)=='"') { b.append('"'); i++; }
                else quoted=!quoted;
            } else if (c==',' && !quoted) { if (f<5) fields[f++]=b.toString(); b.setLength(0); }
            else b.append(c);
        }
        if (f<5) fields[f++]=b.toString();
        while (f<5) fields[f++]="";
        return fields;
    }
    private static Patient[] loadPatients() throws IOException {
        BufferedReader br=Files.newBufferedReader(PATIENTS_PATH, StandardCharsets.UTF_8);
        Patient[] a=new Patient[16]; int n=0; String line; boolean header=true;
        try {
            while ((line=br.readLine())!=null) {
                if (header) { header=false; continue; }
                if (line.trim().isEmpty()) continue;
                String[] x=parseCsvLine(line);
                if (n==a.length) { Patient[] bigger=new Patient[a.length*2]; System.arraycopy(a,0,bigger,0,n); a=bigger; }
                a[n++]=new Patient(x[0],x[1],x[2],x[3],x[4]);
            }
        } finally { br.close(); }
        Patient[] result=new Patient[n]; System.arraycopy(a,0,result,0,n); return result;
    }
    private static void savePatients(Patient[] a) throws IOException {
        BufferedWriter bw=Files.newBufferedWriter(PATIENTS_PATH, StandardCharsets.UTF_8);
        try {
            bw.write("id,name,age,department,history\n");
            for (int i=0;i<a.length;i++) {
                Patient p=a[i]; bw.write(csvEscape(p.id)+","+csvEscape(p.name)+","+csvEscape(p.age)+","+csvEscape(p.department)+","+csvEscape(p.history)+"\n");
            }
        } finally { bw.close(); }
    }
    private static void viewPatients() throws IOException {
        Patient[] p=loadPatients();
        if (p.length==0) { System.out.println("No patient records found."); return; }
        System.out.println("\nPATIENT RECORDS ("+p.length+")");
        for (int i=0;i<p.length;i++) {
            System.out.println("------------------------------------------------------------");
            System.out.println("ID: "+p[i].id+" | Name: "+p[i].name+" | Age: "+p[i].age);
            System.out.println("Department: "+p[i].department);
            System.out.println("History: "+p[i].history);
        }
    }
    private static void addPatient() throws IOException {
        Patient[] old=loadPatients();
        String id=readLine("Patient ID: ");
        for (int i=0;i<old.length;i++) if (old[i].id.equals(id)) { System.out.println("That ID already exists. Choose a unique ID."); return; }
        String name=readLine("Patient name: "); String age=readLine("Age: ");
        String dept=readLine("Department: "); String history=readLine("Medical history/report: ");
        if (id.isEmpty()||name.isEmpty()||age.isEmpty()||dept.isEmpty()) { System.out.println("ID, name, age and department are required; record not saved."); return; }
        Patient[] next=new Patient[old.length+1]; System.arraycopy(old,0,next,0,old.length); next[old.length]=new Patient(id,name,age,dept,history);
        savePatients(next); System.out.println("Patient saved to "+PATIENTS_FILE+". Records persist after restarting the program.");
    }
    private static void searchPatient() throws IOException {
        String q=readLine("Enter patient ID or name fragment: ").toLowerCase(); Patient[] p=loadPatients(); int found=0;
        for (int i=0;i<p.length;i++) if (p[i].id.toLowerCase().equals(q)||p[i].name.toLowerCase().contains(q)) { printPatient(p[i]); found++; }
        if (found==0) System.out.println("No matching patient found.");
    }
    private static void printPatient(Patient p) { System.out.println("ID: "+p.id+" | "+p.name+" | age "+p.age+" | "+p.department+" | "+p.history); }
    private static void deletePatient() throws IOException {
        String id=readLine("Enter exact patient ID to delete: "); Patient[] old=loadPatients(); int idx=-1;
        for (int i=0;i<old.length;i++) if (old[i].id.equals(id)) { idx=i; break; }
        if (idx<0) { System.out.println("No patient with ID "+id+" was found. Nothing deleted."); return; }
        System.out.println("Selected record:"); printPatient(old[idx]);
        String confirm=readLine("Type DELETE to confirm: ");
        if (!"DELETE".equals(confirm)) { System.out.println("Deletion cancelled."); return; }
        Patient[] next=new Patient[old.length-1]; for (int i=0,j=0;i<old.length;i++) if (i!=idx) next[j++]=old[i];
        savePatients(next); System.out.println("Patient deleted and CSV updated.");
    }
    private static String allPatientText() throws IOException {
        Patient[] p=loadPatients(); StringBuilder b=new StringBuilder();
        for (int i=0;i<p.length;i++) b.append("Patient ").append(p[i].id).append(" ").append(p[i].name).append(". ").append(p[i].history).append("\n");
        return b.toString();
    }

    // MODULE 1: TextHack query engine
    private static void textHackQueries() throws Exception {
        while (true) {
            System.out.println("\nTEXT HACK QUERY ENGINE");
            System.out.println("1 Pattern search  2 Fuzzy match  3 Document similarity");
            System.out.println("4 Citation-flow example  5 Project scheduling  6 Large-prime test  7 Back");
            int c=readInt("Query: "); if(c==7) return;
            if(c==1) { String text=readCorpusOrPatientText(); String pat=readLine("Pattern to search: "); int[] pos=kmp(text,pat); printPositions("KMP positions",pos); }
            else if(c==2) { String a=readLine("Entered term: "), b=readLine("Candidate/correct term: "); System.out.println("Edit distance = "+levenshtein(a,b)); }
            else if(c==3) { String a=readLine("Document/text A: "), b=readLine("Document/text B: "); System.out.printf("Character bigram similarity (Jaccard): %.3f%n", jaccardBigrams(a,b)); }
            else if(c==4) { System.out.println("Toy citation graph: Article A cites B and C; B cites C; C cites none."); System.out.println("Choose a source node (A=0, B=1, C=2) to see reachable cited articles."); int s=readInt("Source node: "); int[][] g={{0,1,1},{0,0,1},{0,0,0}}; if(s<0||s>2) System.out.println("Use 0, 1 or 2."); else { boolean[] seen=new boolean[3]; dfsReach(g,s,seen); System.out.print("Reachable: "); for(int i=0;i<3;i++) if(seen[i]) System.out.print((char)('A'+i)+" "); System.out.println(); } }
            else if(c==5) { System.out.println("Enter durations for 3 dependent tasks A -> B -> C."); int a=readInt("Duration A: "), b=readInt("Duration B: "), d=readInt("Duration C: "); System.out.println("Critical-path schedule: A starts 0, B starts "+a+", C starts "+(a+b)+"; total duration = "+(a+b+d)); }
            else if(c==6) { long n=readLong("Integer to test (use n >= 2): "); System.out.println(n+" is "+(millerRabin(n,8)?"probably prime":"composite")+" (probabilistic test)."); }
            else System.out.println("Choose 1–7.");
        }
    }
    private static String readCorpusOrPatientText() throws IOException {
        System.out.println("Text source: 1=sample Wikipedia-style corpus, 2=all patient histories, 3=type text");
        int c=readInt("Source: ");
        if(c==2) return allPatientText();
        if(c==3) return readLine("Enter text: ");
        return new String(Files.readAllBytes(WIKI_PATH), StandardCharsets.UTF_8);
    }
    private static void dfsReach(int[][] g,int u,boolean[] seen) { seen[u]=true; for(int v=0;v<g.length;v++) if(g[u][v]!=0&&!seen[v]) dfsReach(g,v,seen); }
    private static double jaccardBigrams(String a,String b) {
        String aa=a.toLowerCase(), bb=b.toLowerCase(); String[] x=uniqueBigrams(aa), y=uniqueBigrams(bb); int inter=0;
        for(int i=0;i<x.length;i++) for(int j=0;j<y.length;j++) if(x[i].equals(y[j])) { inter++; break; }
        int union=x.length+y.length-inter; return union==0?1.0:(double)inter/union;
    }
    private static String[] uniqueBigrams(String s) { String[] tmp=new String[s.length()]; int n=0; for(int i=0;i+1<s.length();i++){String z=s.substring(i,i+2); boolean found=false; for(int j=0;j<n;j++)if(tmp[j].equals(z)){found=true;break;} if(!found)tmp[n++]=z;} String[] r=new String[n];System.arraycopy(tmp,0,r,0,n);return r; }

    // MODULE 2: String algorithms
    private static void stringAlgorithmsMenu() throws Exception {
        String text=readCorpusOrPatientText(); String pattern=readLine("Pattern / medical term: ");
        System.out.println("Text length="+text.length()+", pattern length="+pattern.length());
        printPositions("Naive",naive(text,pattern)); printPositions("KMP",kmp(text,pattern)); printPositions("Z-function",zSearch(text,pattern)); printPositions("Rabin-Karp",rabinKarp(text,pattern));
        System.out.println("Complexities: Naive O(nm); KMP/Z O(n+m); Rabin-Karp expected O(n+m), worst O(nm).");
        String multi=readLine("Optional comma-separated keywords for Aho-Corasick (blank to skip): ");
        if(!multi.isEmpty()) { String[] pats=splitComma(multi); ahoSearch(text,pats); }
        String term=readLine("Optional word to check in Trie (blank to skip): "); if(!term.isEmpty()) { Trie trie=new Trie(); String[] dict={"diabetes","hypertension","fever","infection","asthma","pneumonia","migraine","anemia"}; for(int i=0;i<dict.length;i++)trie.insert(dict[i]); System.out.println("Trie dictionary contains '"+term+"': "+trie.contains(term.toLowerCase())); }
    }
    private static String[] splitComma(String s) { int n=1; for(int i=0;i<s.length();i++)if(s.charAt(i)==',')n++; String[] a=new String[n]; int start=0,k=0; for(int i=0;i<=s.length();i++)if(i==s.length()||s.charAt(i)==','){a[k++]=s.substring(start,i).trim();start=i+1;} return a; }
    private static int[] naive(String t,String p) { if(p.length()==0)return new int[]{0}; int[] tmp=new int[t.length()+1];int n=0;for(int i=0;i+p.length()<=t.length();i++){int j=0;while(j<p.length()&&t.charAt(i+j)==p.charAt(j))j++;if(j==p.length())tmp[n++]=i;}int[] r=new int[n];System.arraycopy(tmp,0,r,0,n);return r; }
    private static int[] kmp(String t,String p) { if(p.length()==0)return new int[]{0}; int[] lps=new int[p.length()];for(int i=1,len=0;i<p.length();){if(p.charAt(i)==p.charAt(len))lps[i++]=++len;else if(len!=0)len=lps[len-1];else lps[i++]=0;} int[] tmp=new int[t.length()+1];int n=0;for(int i=0,j=0;i<t.length();){if(t.charAt(i)==p.charAt(j)){i++;j++;if(j==p.length()){tmp[n++]=i-j;j=lps[j-1];}}else if(j!=0)j=lps[j-1];else i++;}int[] r=new int[n];System.arraycopy(tmp,0,r,0,n);return r; }
    private static int[] zSearch(String t,String p) { if(p.length()==0)return new int[]{0}; String s=p+"\u0001"+t;int[] z=new int[s.length()];int l=0,r=0;for(int i=1;i<s.length();i++){if(i<=r)z[i]=Math.min(r-i+1,z[i-l]);while(i+z[i]<s.length()&&s.charAt(z[i])==s.charAt(i+z[i]))z[i]++;if(i+z[i]-1>r){l=i;r=i+z[i]-1;}}int[] tmp=new int[t.length()+1];int n=0;for(int i=p.length()+1;i<s.length();i++)if(z[i]>=p.length())tmp[n++]=i-p.length()-1;int[] out=new int[n];System.arraycopy(tmp,0,out,0,n);return out; }
    private static int[] rabinKarp(String t,String p) { if(p.length()==0)return new int[]{0};int m=p.length(),n=t.length();int[] tmp=new int[n+1];int k=0;long mod1=1000000007L,mod2=1000000009L,base=257,hp1=0,hp2=0,ht1=0,ht2=0,pow1=1,pow2=1;if(m>n)return new int[0];for(int i=0;i<m;i++){hp1=(hp1*base+p.charAt(i))%mod1;hp2=(hp2*base+p.charAt(i))%mod2;ht1=(ht1*base+t.charAt(i))%mod1;ht2=(ht2*base+t.charAt(i))%mod2;if(i<m-1){pow1=pow1*base%mod1;pow2=pow2*base%mod2;}}for(int i=0;i<=n-m;i++){if(hp1==ht1&&hp2==ht2&&t.regionMatches(i,p,0,m))tmp[k++]=i;if(i<n-m){ht1=(ht1-(t.charAt(i)*pow1)%mod1+mod1)%mod1;ht1=(ht1*base+t.charAt(i+m))%mod1;ht2=(ht2-(t.charAt(i)*pow2)%mod2+mod2)%mod2;ht2=(ht2*base+t.charAt(i+m))%mod2;}}int[] r=new int[k];System.arraycopy(tmp,0,r,0,k);return r; }
    private static void printPositions(String label,int[] a) { System.out.print(label+": "); if(a.length==0)System.out.print("no match"); else for(int i=0;i<a.length;i++)System.out.print((i==0?"":", ")+a[i]);System.out.println(); }
    private static class ACNode { int[] next=new int[128]; int fail=0; int[] output; int outCount=0; ACNode(){for(int i=0;i<128;i++)next[i]=-1;output=new int[4];} void addOutput(int id){if(outCount==output.length){int[]b=new int[output.length*2];System.arraycopy(output,0,b,0,outCount);output=b;}output[outCount++]=id;} }
    private static void ahoSearch(String text,String[] patterns) {
        int capacity=1; for(int i=0;i<patterns.length;i++)capacity+=patterns[i].length();
        ACNode[] nodes=new ACNode[capacity];int count=1;nodes[0]=new ACNode();
        for(int id=0;id<patterns.length;id++){String p=patterns[id];if(p.isEmpty())continue;int u=0;boolean supported=true;for(int j=0;j<p.length();j++){char ch=p.charAt(j);if(ch>=128){supported=false;break;}if(nodes[u].next[ch]<0){nodes[u].next[ch]=count;nodes[count++]=new ACNode();}u=nodes[u].next[ch];}if(supported)nodes[u].addOutput(id);}
        int[] q=new int[count];int head=0,tail=0;for(int ch=0;ch<128;ch++){int v=nodes[0].next[ch];if(v>=0){nodes[v].fail=0;q[tail++]=v;}else nodes[0].next[ch]=0;}
        while(head<tail){int u=q[head++];for(int ch=0;ch<128;ch++){int v=nodes[u].next[ch];if(v>=0){nodes[v].fail=nodes[nodes[u].fail].next[ch];ACNode f=nodes[nodes[v].fail];for(int k=0;k<f.outCount;k++)nodes[v].addOutput(f.output[k]);q[tail++]=v;}else nodes[u].next[ch]=nodes[nodes[u].fail].next[ch];}}
        int[][] positions=new int[patterns.length][text.length()+1];int[] counts=new int[patterns.length];int state=0;
        for(int i=0;i<text.length();i++){char ch=text.charAt(i);if(ch>=128){state=0;continue;}state=nodes[state].next[ch];for(int k=0;k<nodes[state].outCount;k++){int id=nodes[state].output[k];if(counts[id]<positions[id].length)positions[id][counts[id]++]=i-patterns[id].length()+1;}}
        System.out.println("Aho-Corasick matches (Trie + failure links, one text scan):");for(int i=0;i<patterns.length;i++){int[]r=new int[counts[i]];System.arraycopy(positions[i],0,r,0,counts[i]);printPositions("  "+patterns[i],r);}System.out.println("Time: O(total pattern length + text length + matches) for the ASCII teaching implementation.");
    }
    static class TrieNode { TrieNode[] next=new TrieNode[128]; boolean end; }
    static class Trie { TrieNode root=new TrieNode(); void insert(String s){TrieNode n=root;for(int i=0;i<s.length();i++){char c=s.charAt(i);if(c>=128)return;if(n.next[c]==null)n.next[c]=new TrieNode();n=n.next[c];}n.end=true;} boolean contains(String s){TrieNode n=root;for(int i=0;i<s.length();i++){char c=s.charAt(i);if(c>=128||n.next[c]==null)return false;n=n.next[c];}return n.end;} }
    private static void suffixStructures() throws IOException {
        String s=readLine("Enter text to index (or type PATIENTS to use all patient history): "); if(s.equalsIgnoreCase("PATIENTS"))s=allPatientText();
        int[] sa=suffixArray(s); int[] lcp=kasai(s,sa);System.out.println("Suffix array (start positions):");for(int i=0;i<sa.length;i++)System.out.println(i+": position="+sa[i]+" suffix=\""+s.substring(sa[i])+"\" LCP="+lcp[i]);
        System.out.println("Construction: this compact implementation sorts suffix strings directly (educational baseline, O(n^2 log n) comparison work in typical analysis); SA-IS and prefix-doubling improvements are described conceptually, not implemented here.");
        System.out.println("Suffix trees/automata preview: these structures can support substring queries and end-position equivalence efficiently, but are not built by this demo.");
    }
    private static int[] suffixArray(String s) { int n=s.length();int[] sa=new int[n];for(int i=0;i<n;i++)sa[i]=i;for(int i=1;i<n;i++){int x=sa[i],j=i-1;while(j>=0&&s.substring(sa[j]).compareTo(s.substring(x))>0){sa[j+1]=sa[j];j--;}sa[j+1]=x;}return sa; }
    private static int[] kasai(String s,int[] sa) { int n=s.length();int[] rank=new int[n],lcp=new int[n];for(int i=0;i<n;i++)rank[sa[i]]=i;int h=0;for(int i=0;i<n;i++){int r=rank[i];if(r==0)continue;int j=sa[r-1];while(i+h<n&&j+h<n&&s.charAt(i+h)==s.charAt(j+h))h++;lcp[r]=h;if(h>0)h--;}return lcp; }

    // MODULE 3: Advanced DP
    private static void dynamicProgrammingMenu() throws Exception {
        while(true){System.out.println("\nDP LAB: 1 Edit distances 2 Sequence alignment 3 Matrix chain (interval DP) 4 Bitmask TSP 5 Tree-DP preview 6 SOS DP 7 Back");int c=readInt("Choose: ");if(c==7)return;
            if(c==1){String a=readLine("Source term: "),b=readLine("Target term: ");System.out.println("Levenshtein/Wagner-Fischer = "+levenshtein(a,b));System.out.println("Damerau-Levenshtein = "+damerau(a,b));int ins=readInt("Weighted insertion cost: "),del=readInt("Weighted deletion cost: "),sub=readInt("Weighted substitution cost: ");System.out.println("Weighted edit cost = "+weightedEdit(a,b,ins,del,sub));}
            else if(c==2){String a=readLine("Sequence A (e.g. ACGTAC): "),b=readLine("Sequence B (e.g. ACGTC): ");int match=readInt("Match score (e.g. 2): "),mismatch=readInt("Mismatch score (e.g. -1): "),gap=readInt("Gap penalty (e.g. -2): ");System.out.println("Needleman-Wunsch global alignment score = "+needleman(a,b,match,mismatch,gap));System.out.println("Smith-Waterman local alignment score = "+smithWaterman(a,b,match,mismatch,gap));}
            else if(c==3){int n=readInt("Number of matrices (2-12): ");if(n<2||n>12){System.out.println("Use 2–12 matrices.");continue;}int[] d=new int[n+1];for(int i=0;i<=n;i++)d[i]=readInt("Dimension d["+i+"]: ");System.out.println("Minimum scalar multiplications = "+matrixChain(d));}
            else if(c==4){int n=readInt("Number of TSP cities (2-15): ");if(n<2||n>15){System.out.println("Use 2–15 cities.");continue;}int[][] w=new int[n][n];for(int i=0;i<n;i++)for(int j=0;j<n;j++)if(i!=j)w[i][j]=readInt("Cost "+i+" -> "+j+": ");System.out.println("Minimum tour cost (start city 0) = "+tsp(w));System.out.println("Complexity: O(n^2 2^n) time, O(n 2^n) space.");}
            else if(c==5){System.out.println("Tree-DP preview: enter node values for a rooted tree, then compute a tree aggregate. This demo uses a path tree.");int n=readInt("Number of nodes (1-100): ");if(n<1||n>100)continue;long sum=0;int[] v=new int[n];for(int i=0;i<n;i++){v[i]=readInt("Value at node "+i+": ");sum+=v[i];}System.out.println("Path-tree DP aggregate sum = "+sum+"; path diameter in edges = "+(n-1));System.out.println("Tree DP / rerooting are represented as a guided preview, not a full general-tree library.");}
            else if(c==6){int n=readInt("Number of bits (1-20): ");if(n<1||n>20)continue;int size=1<<n;long[] a=new long[size];for(int i=0;i<size;i++)a[i]=readLong("Value for mask "+bits(i,n)+": ");for(int bit=0;bit<n;bit++)for(int mask=0;mask<size;mask++)if((mask&(1<<bit))!=0)a[mask]+=a[mask^(1<<bit)];System.out.println("SOS DP subset sums:");for(int mask=0;mask<size;mask++)System.out.println(bits(mask,n)+" -> "+a[mask]);System.out.println("Time O(n*2^n), space O(2^n).");}
            else System.out.println("Choose 1–7.");
        }
    }
    private static String bits(int x,int n){StringBuilder b=new StringBuilder();for(int i=n-1;i>=0;i--)b.append((x&(1<<i))==0?'0':'1');return b.toString();}
    private static int levenshtein(String a,String b){int[][] d=new int[a.length()+1][b.length()+1];for(int i=0;i<=a.length();i++)d[i][0]=i;for(int j=0;j<=b.length();j++)d[0][j]=j;for(int i=1;i<=a.length();i++)for(int j=1;j<=b.length();j++)d[i][j]=Math.min(Math.min(d[i-1][j]+1,d[i][j-1]+1),d[i-1][j-1]+(a.charAt(i-1)==b.charAt(j-1)?0:1));return d[a.length()][b.length()];}
    private static int damerau(String a,String b){int[][] d=new int[a.length()+1][b.length()+1];for(int i=0;i<=a.length();i++)d[i][0]=i;for(int j=0;j<=b.length();j++)d[0][j]=j;for(int i=1;i<=a.length();i++)for(int j=1;j<=b.length();j++){int cost=a.charAt(i-1)==b.charAt(j-1)?0:1;d[i][j]=Math.min(Math.min(d[i-1][j]+1,d[i][j-1]+1),d[i-1][j-1]+cost);if(i>1&&j>1&&a.charAt(i-1)==b.charAt(j-2)&&a.charAt(i-2)==b.charAt(j-1))d[i][j]=Math.min(d[i][j],d[i-2][j-2]+1);}return d[a.length()][b.length()];}
    private static int weightedEdit(String a,String b,int ins,int del,int sub){int[][]d=new int[a.length()+1][b.length()+1];for(int i=1;i<=a.length();i++)d[i][0]=d[i-1][0]+del;for(int j=1;j<=b.length();j++)d[0][j]=d[0][j-1]+ins;for(int i=1;i<=a.length();i++)for(int j=1;j<=b.length();j++)d[i][j]=Math.min(Math.min(d[i-1][j]+del,d[i][j-1]+ins),d[i-1][j-1]+(a.charAt(i-1)==b.charAt(j-1)?0:sub));return d[a.length()][b.length()];}
    private static int needleman(String a,String b,int match,int mismatch,int gap){int[][]d=new int[a.length()+1][b.length()+1];for(int i=1;i<=a.length();i++)d[i][0]=d[i-1][0]+gap;for(int j=1;j<=b.length();j++)d[0][j]=d[0][j-1]+gap;for(int i=1;i<=a.length();i++)for(int j=1;j<=b.length();j++)d[i][j]=Math.max(Math.max(d[i-1][j-1]+(a.charAt(i-1)==b.charAt(j-1)?match:mismatch),d[i-1][j]+gap),d[i][j-1]+gap);return d[a.length()][b.length()];}
    private static int smithWaterman(String a,String b,int match,int mismatch,int gap){int[][]d=new int[a.length()+1][b.length()+1];int best=0;for(int i=1;i<=a.length();i++)for(int j=1;j<=b.length();j++){d[i][j]=Math.max(0,Math.max(Math.max(d[i-1][j-1]+(a.charAt(i-1)==b.charAt(j-1)?match:mismatch),d[i-1][j]+gap),d[i][j-1]+gap));if(d[i][j]>best)best=d[i][j];}return best;}
    private static long matrixChain(int[] d){int n=d.length-1;long[][]m=new long[n][n];for(int len=2;len<=n;len++)for(int i=0;i+len<=n;i++){int j=i+len-1;m[i][j]=Long.MAX_VALUE/4;for(int k=i;k<j;k++){long q=m[i][k]+m[k+1][j]+(long)d[i]*d[k+1]*d[j+1];if(q<m[i][j])m[i][j]=q;}}return m[0][n-1];}
    private static long tsp(int[][]w){int n=w.length,size=1<<n;long inf=Long.MAX_VALUE/8;long[][]dp=new long[size][n];for(int m=0;m<size;m++)for(int j=0;j<n;j++)dp[m][j]=inf;dp[1][0]=0;for(int mask=0;mask<size;mask++)if((mask&1)!=0)for(int u=0;u<n;u++)if(dp[mask][u]<inf)for(int v=0;v<n;v++)if((mask&(1<<v))==0){int nm=mask|(1<<v);long val=dp[mask][u]+w[u][v];if(val<dp[nm][v])dp[nm][v]=val;}long ans=inf;for(int u=1;u<n;u++)if(dp[size-1][u]<inf&&dp[size-1][u]+w[u][0]<ans)ans=dp[size-1][u]+w[u][0];return ans>=inf?-1:ans;}

    // MODULE 4: max flow / matching
    private static void networkFlowMenu() throws Exception {System.out.println("Flow lab: enter a directed network; nodes numbered 0..n-1, source 0, sink n-1.");int n=readInt("Number of nodes (2-20): ");if(n<2||n>20)return;int[][]cap=new int[n][n];int m=readInt("Number of directed edges: ");for(int i=0;i<m;i++){int u=readInt("Edge "+(i+1)+" from: "),v=readInt("Edge "+(i+1)+" to: "),c=readInt("Capacity: ");if(u>=0&&u<n&&v>=0&&v<n&&c>=0)cap[u][v]+=c;else System.out.println("Invalid edge; skipped.");}int method=readInt("Algorithm 1=Ford-Fulkerson DFS, 2=Edmonds-Karp BFS: ");int flow=maxFlow(cap,0,n-1,method==2);System.out.println("Maximum flow = "+flow);System.out.println("Max-flow/min-cut theorem: the maximum flow equals the capacity of a minimum s-t cut.");System.out.println("Dinic and min-cost max-flow are covered conceptually in README; this interactive implementation provides Ford-Fulkerson/Edmonds-Karp.");System.out.println("Bipartite matching reduction: source -> left vertices -> right vertices -> sink, each capacity 1; then run max flow.");}
    private static int maxFlow(int[][] cap,int s,int t,boolean bfs){int n=cap.length;int[][]r=new int[n][n];for(int i=0;i<n;i++)System.arraycopy(cap[i],0,r[i],0,n);int flow=0;while(true){int[]par=new int[n];for(int i=0;i<n;i++)par[i]=-1;boolean found;if(bfs){int[]q=new int[n];int h=0,tail=0;q[tail++]=s;par[s]=s;while(h<tail&&par[t]<0){int u=q[h++];for(int v=0;v<n;v++)if(par[v]<0&&r[u][v]>0){par[v]=u;q[tail++]=v;if(v==t)break;}}found=par[t]>=0;}else{boolean[]seen=new boolean[n];found=dfsPath(r,s,t,seen,par);}if(!found)break;int aug=Integer.MAX_VALUE;for(int v=t;v!=s;v=par[v])aug=Math.min(aug,r[par[v]][v]);for(int v=t;v!=s;v=par[v]){int u=par[v];r[u][v]-=aug;r[v][u]+=aug;}flow+=aug;}return flow;}
    private static boolean dfsPath(int[][]r,int u,int t,boolean[]seen,int[]par){if(u==t)return true;seen[u]=true;for(int v=0;v<r.length;v++)if(!seen[v]&&r[u][v]>0){par[v]=u;if(dfsPath(r,v,t,seen,par))return true;}return false;}


    // MODULE 5: reductions / approximation
    private static void npCompletenessMenu() throws Exception {System.out.println("NP-completeness lab");System.out.println("1. Show reduction chain 3-SAT -> CLIQUE -> INDEPENDENT SET -> VERTEX COVER");System.out.println("2. Vertex-Cover 2-approximation using a maximal-matching style greedy method");System.out.println("3. Explain P, NP, co-NP, NP-hard, NP-complete");int c=readInt("Choose: ");if(c==1){System.out.println("3-SAT: satisfiability with 3-literal clauses. Reduction builds a graph where choosing one literal per clause forms a clique iff the formula is satisfiable.");System.out.println("CLIQUE in G <-> INDEPENDENT-SET in complement(G). A set S is independent iff V\\S is a vertex cover.");System.out.println("Polynomial-time reductions compare problem difficulty; Cook-Levin establishes SAT as NP-complete.");}
        else if(c==2){int n=readInt("Number of vertices (2-50): ");if(n<2||n>50)return;int[][]g=new int[n][n];int m=readInt("Number of undirected edges: ");for(int i=0;i<m;i++){int u=readInt("u: "),v=readInt("v: ");if(u>=0&&u<n&&v>=0&&v<n&&u!=v)g[u][v]=g[v][u]=1;}boolean[]covered=new boolean[n];System.out.println("Approximate vertex cover (both endpoints of each uncovered edge):");for(int u=0;u<n;u++)for(int v=u+1;v<n;v++)if(g[u][v]==1&&!covered[u]&&!covered[v]){covered[u]=covered[v]=true;System.out.println("Select "+u+" and "+v);}int count=0;for(int i=0;i<n;i++)if(covered[i])count++;System.out.println("Cover size = "+count+". This standard 2-approximation is at most twice the optimum for the vertex-cover problem.");}
        else if(c==3)System.out.println("P: solvable in polynomial time. NP: a proposed solution can be verified in polynomial time. co-NP: complements of NP languages. NP-hard: every NP problem reduces to it. NP-complete: both in NP and NP-hard. These are complexity classes, not claims that every instance is difficult.");}

    // MODULE 6: randomized and parallel algorithms
    private static void randomizedParallelMenu() throws Exception {System.out.println("1 Randomized QuickSort 2 Miller-Rabin primality 3 Reservoir sampling 4 Parallel reduce/scan demo 5 Randomized hashing concept");int c=readInt("Choose: ");if(c==1){int n=readInt("Array length (1-1000): ");if(n<1||n>1000)return;int[]a=new int[n];for(int i=0;i<n;i++)a[i]=readInt("a["+i+"]: ");quickSort(a,0,n-1);System.out.print("Sorted: ");for(int i=0;i<n;i++)System.out.print(a[i]+(i+1==n?"\n":" "));System.out.println("Expected time O(n log n), worst case O(n^2); random pivot reduces systematic bad splits.");}
        else if(c==2){long n=readLong("Integer n (>=2): ");System.out.println(millerRabin(n,10)?"Probably prime (Miller-Rabin).":"Composite (Miller-Rabin).");}
        else if(c==3){int n=readInt("Number of stream values: ");if(n<1||n>10000)return;int k=readInt("Reservoir size k (1..n): ");if(k<1||k>n)return;int[]res=new int[k];for(int i=0;i<n;i++){int x=readInt("Stream value "+(i+1)+": ");if(i<k)res[i]=x;else {int j=(int)(Math.random()*(i+1));if(j<k)res[j]=x;}}System.out.print("Uniform reservoir sample: ");for(int i=0;i<k;i++)System.out.print(res[i]+(i+1==k?"\n":" "));}
        else if(c==4){int n=readInt("Number of values: ");if(n<1||n>10000)return;long[]a=new long[n];for(int i=0;i<n;i++)a[i]=readLong("Value "+i+": ");long sum=parallelReduce(a);long[]pref=blellochStyleScan(a);System.out.println("Reduce sum = "+sum);System.out.print("Inclusive prefix scan: ");for(int i=0;i<n;i++)System.out.print(pref[i]+(i+1==n?"\n":" "));System.out.println("Work O(n); ideal parallel span O(log n) for tree-based reduce/scan. This sequential demo illustrates the primitive, not actual multithreaded execution.");}
        else if(c==5)System.out.println("Randomized hashing chooses hash functions from a family to reduce adversarial collisions. Perfect hashing can provide worst-case O(1) lookup for a fixed set after construction; details are described in README.");}
    private static void quickSort(int[]a,int lo,int hi){if(lo>=hi)return;int p=lo+(int)(Math.random()*(hi-lo+1));int tmp=a[p];a[p]=a[hi];a[hi]=tmp;int pivot=a[hi],i=lo-1;for(int j=lo;j<hi;j++)if(a[j]<=pivot){i++;tmp=a[i];a[i]=a[j];a[j]=tmp;}tmp=a[i+1];a[i+1]=a[hi];a[hi]=tmp;int q=i+1;quickSort(a,lo,q-1);quickSort(a,q+1,hi);}
    private static long parallelReduce(long[]a){long s=0;for(int i=0;i<a.length;i++)s+=a[i];return s;}
    private static long[] blellochStyleScan(long[]a){long[]p=new long[a.length];long s=0;for(int i=0;i<a.length;i++){s+=a[i];p[i]=s;}return p;}
    private static boolean millerRabin(long n,int rounds){if(n<2)return false;int[]small={2,3,5,7,11,13,17,19,23,29,31,37};for(int p:small){if(n==p)return true;if(n%p==0)return false;}long d=n-1;int s=0;while((d&1)==0){d>>=1;s++;}for(int i=0;i<rounds;i++){long a=2+(long)(Math.random()*(n-3));long x=modPow(a,d,n);if(x==1||x==n-1)continue;boolean witness=true;for(int r=1;r<s;r++){x=mulMod(x,x,n);if(x==n-1){witness=false;break;}}if(witness)return false;}return true;}
    private static long modPow(long a,long e,long m){long r=1;while(e>0){if((e&1)!=0)r=mulMod(r,a,m);a=mulMod(a,a,m);e>>=1;}return r;}
    private static long mulMod(long a,long b,long m){ // overflow-safe repeated doubling for positive long inputs
        long r=0;a%=m;while(b>0){if((b&1)!=0)r=addMod(r,a,m);a=addMod(a,a,m);b>>=1;}return r;
    }
    private static long addMod(long a,long b,long m){return a>=m-b?a-(m-b):a+b;}

    private static void runAllDemo() throws Exception {
        System.out.println("\nAUTOMATIC SMOKE DEMO");String t="Patient has diabetes and hypertension; diabetes control is important.";String p="diabetes";
        System.out.println("Text: "+t+"\nPattern: "+p);printPositions("Naive",naive(t,p));printPositions("KMP",kmp(t,p));printPositions("Z",zSearch(t,p));printPositions("Rabin-Karp",rabinKarp(t,p));
        System.out.println("Edit distance (diabete -> diabetes) = "+levenshtein("diabete","diabetes"));System.out.println("Global alignment score (ACGTAC, ACGTC) = "+needleman("ACGTAC","ACGTC",2,-1,-2));
        System.out.println("Matrix-chain sample cost = "+matrixChain(new int[]{10,30,5,60}));
        int[][] c={{0,3,2,0},{0,0,0,2},{0,0,0,3},{0,0,0,0}};System.out.println("Flow sample max-flow = "+maxFlow(c,0,3,true));
        System.out.println("Miller-Rabin: 97 is "+(millerRabin(97,8)?"probably prime":"composite"));
        System.out.println("Patient CSV currently contains "+loadPatients().length+" record(s).");
        System.out.println("Smoke demo complete. Use individual menus for interactive inputs and detailed outputs.");
    }
}
