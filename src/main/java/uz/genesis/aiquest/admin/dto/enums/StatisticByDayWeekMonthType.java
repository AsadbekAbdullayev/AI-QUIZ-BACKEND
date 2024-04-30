package uz.genesis.aiquest.admin.dto.enums;

public enum StatisticByDayWeekMonthType {
    DAILY("1 day", "day"),WEEKLY("1 week","week"), MONTHLY("1 month", "month");
    private final String subtractingDayAmount;
    private final String daily;

    StatisticByDayWeekMonthType(String oneDay, String daily){
        this.subtractingDayAmount = oneDay;
        this.daily = daily;
    }

    public String getSubtractingDayAmount() {
        return subtractingDayAmount;
    }

    public String getDaily() {
        return daily;
    }
}
