package com.hijiyam_koubou.taplans;

import java.util.Date;

/**
 * 一件分の予定内容
 * カレンダーのログイベント:https://support.google.com/a/answer/6110475?hl=ja
 * Events: list  https://developers.google.com/calendar/api/v3/reference/events/list?hl=ja
 * */
public class ScheduleItems {
    /**
     * カレンダー ID
     * */
    public String calendarId;

    /**
     * （必須）予定の名前
     * */
    public String subject;

    /**
     * StartDate
     * （必須）予定の開始日
     * */
    public Date startDate;

    /**
     * 予定の開始時刻
     * */
    public Date startTime;

    /**
     * EndDate
     * 予定の終了日
     * */
    public Date endDate;

    /**
      * endTime
     * 予定の終了時刻
      * */
    public Date endTime;

    /**
     * 終日の予定
     * */
    public Boolean allDayEvent = false;

    /**
     * 予定についての説明またはメモ
     * */
    public String description;

    /**
     * 予定の場所
     * */
    public String location;

    /**
     * 予定の色。
     * */
    public String colorId;

    /**
      * 予定を限定公開にするかどうかを指定します。
      * */
    public Boolean isPrivate =false;

    /**
     * クライアントサイド暗号化
     * */
    /**
      * 予定 ID
      * */
    /**
     * 予定の開始時間
     * */

    /**
     * 予定のタイトル
     * */
    /**
     * 相互運用に関するエラーコード
     * */

    /**
     * IP アドレス
     * */

    /**
     * 新しい値
     * */

    /**
     * 通知メッセージ ID
     * */
    /**
     * 通知方法
     * */

    /**
     *　通知の種類
     * */
    /**
     *　予定の古いタイトル
     * */

    /**
     *　主催者のカレンダー ID
     * */
    /**
     *　定期的
     * */

    /**
     * リモート Exchange サーバーの URL*
     * */

    /**
     * リクエストした期間の終了時刻*
     * */

    /**
     * リクエストした期間の開始時刻*
     * */
    /**
     * 登録者のカレンダー ID
     * */

    /**
     * ターゲット
     * */
    /**
     *　ユーザー エージェント
     * */

}
