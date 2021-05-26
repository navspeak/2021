package nav.recurison;

import java.util.*;

class Program {

    public static void main(String[] args) {
        ProgramTest t = new ProgramTest();
        t.TestCase1();
    }

    public static OrgChart getLowestCommonManager_______(
            OrgChart topManager, OrgChart reportOne, OrgChart reportTwo) {
        Deque<OrgChart> h1 = getHierarchy(topManager, reportOne);
        Deque<OrgChart> h2 = getHierarchy(topManager, reportTwo);
        OrgChart ret = topManager;
        while(!h1.isEmpty() && !h2.isEmpty()){
            OrgChart one = h1.poll();
            OrgChart two = h2.poll();
            if (one == two)
                ret = one;
        }

        return ret; // Replace this line.
    }



    public static OrgChart getLowestCommonManager(
            OrgChart topManager, OrgChart reportOne, OrgChart reportTwo) {
        OrgChart ret = getLowestCommonManager_helper(topManager, reportOne, reportTwo);
        return ret == null ? topManager: ret;
    }
    public static OrgChart getLowestCommonManager_helper(
            OrgChart topManager, OrgChart reportOne, OrgChart reportTwo) {
        if (topManager == null) return null;
        if (topManager == reportOne || topManager == reportTwo) {
            return topManager;
        }
        int count = 0;
        List<OrgChart> list = new ArrayList<>(2);
        for (OrgChart child : topManager.directReports) {
            OrgChart ret = getLowestCommonManager_helper(child, reportOne, reportTwo);
            if (ret != null){
                list.add(ret);
            }
            if (list.size() == 2) break;
        }
        if (list.size() == 2) {
            return topManager;
        } else if (list.size() == 1){
            return list.remove(0);
        } else
            return null;
    }

        private static Deque<OrgChart> getHierarchy(OrgChart topManager, OrgChart reportee){
        Deque<OrgChart> path = new ArrayDeque<>();
        getHierarchy(topManager, reportee, path);
        return path;
    }

    private static boolean getHierarchy(OrgChart head, OrgChart node, Deque<OrgChart> path){
        if (head == null)	return false;
        if (head ==node) return true;
        path.add(head);
        for (OrgChart child: head.directReports){
            if (getHierarchy(child, node, path) == true)
                return true;
        }
        path.removeLast();
        return false;
    }

    static class OrgChart {
        public char name;
        public List<OrgChart> directReports;

        OrgChart(char name) {
            this.name = name;
            this.directReports = new ArrayList<OrgChart>();
        }

        @Override
        public String toString() {
            StringBuilder ret =  new StringBuilder();
            ret.append(name).append("[");
            directReports.forEach(x -> ret.append(x).append(" - "));
            ret.append(" ]");
            return ret.toString();
        }

        // This method is for testing only.
        public void addDirectReports(OrgChart[] directReports) {
            for (OrgChart directReport : directReports) {
                this.directReports.add(directReport);
            }
        }
    }
}



class ProgramTest {

    public HashMap<Character, Program.OrgChart> getOrgCharts() {
        var orgCharts = new HashMap<Character, Program.OrgChart>();
        var alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (char a : alphabet.toCharArray()) {
            orgCharts.put(a, new Program.OrgChart(a));
        }
        orgCharts.get('X').addDirectReports(new Program.OrgChart[] {orgCharts.get('Z')});
        return orgCharts;
    }

    /*

                          A
                   b             c        d         e
               g  h  i          j        k l

     */



    public void TestCase1() {
        var orgCharts = getOrgCharts();
        orgCharts
                .get('A')
                .addDirectReports(new Program.OrgChart[] {orgCharts.get('B'), orgCharts.get('C')});
        orgCharts
                .get('B')
                .addDirectReports(new Program.OrgChart[] {orgCharts.get('D'), orgCharts.get('E')});
        orgCharts
                .get('C')
                .addDirectReports(new Program.OrgChart[] {orgCharts.get('F'), orgCharts.get('G')});
        orgCharts
                .get('D')
                .addDirectReports(new Program.OrgChart[] {orgCharts.get('H'), orgCharts.get('I')});

        Program.OrgChart lcm =
                Program.getLowestCommonManager(orgCharts.get('A'), orgCharts.get('E'), orgCharts.get('I'));
        System.out.println(lcm.name);
    }
}



