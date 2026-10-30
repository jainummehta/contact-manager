package logic;

import model.Contact;

public class FuzzySearch {

    public static int editDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }
        return dp[a.length()][b.length()];
    }

    public static int score(String query, Contact c) {
        String q = query.toLowerCase().trim();
        if (q.isEmpty()) return 1;

        String name = c.getName().toLowerCase();
        if (name.startsWith(q)) return 100;
        if (name.contains(q)) return 80;

        String qDigits = q.replaceAll("\\D", "");
        if (!qDigits.isEmpty() && c.getPhone().replaceAll("\\D", "").contains(qDigits)) return 70;
        if (c.getEmail().toLowerCase().contains(q)) return 60;
        if (c.getExtra().toLowerCase().contains(q)) return 55;

        int allowed = q.length() <= 3 ? 0 : (q.length() <= 5 ? 1 : 2);
        for (String word : name.split("\\s+")) {
            String start = word.length() > q.length() ? word.substring(0, q.length()) : word;
            int d = Math.min(editDistance(q, word), editDistance(q, start));
            if (d <= allowed) return 50 - d * 10;
        }
        return 0;
    }
}
