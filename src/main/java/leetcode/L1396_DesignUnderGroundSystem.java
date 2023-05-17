package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhaochengming
 * @date : 2023/5/17 15:23
 * @description : https://leetcode.cn/problems/design-underground-system/
 * 设计地铁系统
 */
public class L1396_DesignUnderGroundSystem {
    static class UndergroundSystem {

        static class Info {
            int id;
            String station;
            int t;

            Info(int id, String station, int t) {
                this.id = id;
                this.station = station;
                this.t = t;
            }
        }

        Map<Integer, Info> userLogs;
        Map<String, int[]> timeSummary;

        public UndergroundSystem() {
            this.userLogs = new HashMap<>();
            this.timeSummary = new HashMap<>();
        }

        public void checkIn(int id, String stationName, int t) {
            userLogs.put(id, new Info(id, stationName, t));
        }

        public void checkOut(int id, String stationName, int t) {
            Info checkInInfo = userLogs.remove(id);
            String key = checkInInfo.station + "->" + stationName;
            int[] summary = timeSummary.getOrDefault(key, new int[2]);
            summary[0] += t - checkInInfo.t;
            summary[1] ++;
            timeSummary.put(key, summary);
        }

        public double getAverageTime(String startStation, String endStation) {
            String key = startStation + "->" + endStation;
            int[] summary = timeSummary.get(key);
            return (double) summary[0] / summary[1];
        }
    }
}
