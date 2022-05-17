package com.amansprojects.citrusdev.ffa;

public class StatisticCase implements Comparable<StatisticCase> {
    public final String UUID;
    public final Integer KILLS;
    public final Integer DEATHS;
    public StatisticCase(String uuid, Integer kills, Integer deaths) {
        UUID = uuid;
        KILLS = kills;
        DEATHS = deaths;
    }
    @Override
    public int compareTo(StatisticCase otherCase) {
        return Integer.compare(KILLS, otherCase.KILLS);
    }

    public static class StatisticCaseKDR extends StatisticCase {
        public StatisticCaseKDR(String uuid, Integer kills, Integer deaths) {
            super(uuid, kills, deaths);
        }

        @Override
        public int compareTo(StatisticCase otherCase) {
            return Float.compare((float)KILLS/DEATHS, (float)otherCase.KILLS/otherCase.DEATHS);
        }
    }
}
