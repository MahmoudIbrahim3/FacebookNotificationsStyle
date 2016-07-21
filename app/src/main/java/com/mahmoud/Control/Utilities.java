package com.mahmoud.Control;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.mahmoud.facebooknotificationsstyle.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utilities extends Application {

	private static String TAG = "Utilities";

	public static String[] daysOfWeek;
	public static String[] months;
	public static Date currentDate;
	static String[] monthesKeywords = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Nov", "Dec"};

	@Override
	public void onCreate() {
		super.onCreate();

		daysOfWeek = getResources().getStringArray(R.array.days_of_weeks);
		months = getResources().getStringArray(R.array.months);
		currentDate = Utilities.getCurrentDate(Keys.CONST_NOTIFICATION_DATE_FORMAT);
	}

	public static Date getCurrentDate(String format){
		Date cDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date = simpleDateFormat.format(cDate);

		Date currentDate = null;
		try {
			currentDate = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return currentDate;
	}

	/**
	 * Get the date in this format: dd (EEE), (ex: 14 Apr).
	 * @param notifDate
	 * @return
	 */
	private static String getDateIn_DDEEE(Date notifDate) {
		SimpleDateFormat format4 = new SimpleDateFormat("dd MMM", Locale.US);

		String[] date = format4.format(notifDate.getTime()).split(" ");

		for (int i = 0; i < monthesKeywords.length; i++) {
			if(date[1].equalsIgnoreCase(monthesKeywords[i])){
				return date[0] + " " + months[i];
			}
		}

		return "";
	}

	private static String computeTime(Context context, String timePart){
		String[] temp = timePart.split(":");

		Integer hours = Integer.parseInt(temp[0]);
		String time;
		if(hours == 12){
			time = hours + ":" + temp[1] + " " +
					context.getResources().getString(R.string.pm);
		}
		else if(hours > 12){
			time = (hours - 12) + ":" + temp[1] + " " +
					context.getResources().getString(R.string.pm);
		}
		else {
			time = hours + ":" + temp[1] + " " +
					context.getResources().getString(R.string.am);
		}

		return time;
	}

	public static String getDate(Context context, String date) {
		String finalDate = "";

		if (!date.equals("")) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						Keys.CONST_NOTIFICATION_DATE_FORMAT, Locale.US);
				Date notifDate = simpleDateFormat.parse(date);

				long different = currentDate.getTime() - notifDate.getTime();
				String[] temp = date.split(" ");

				// This condition required because of the server time for some reason returned
				// larger than the current time by one minute and that will cause the "different"
				// parameter has a negative value.
				if(different < 0)
					different  = 1000;

				long secondsInMilli = 1000;
				long minutesInMilli = secondsInMilli * 60;
				long hoursInMilli = minutesInMilli * 60;
				long daysInMilli = hoursInMilli * 24;

				long elapsedDays = different / daysInMilli;
				different = different % daysInMilli;

				long elapsedHours = different / hoursInMilli;
				different = different % hoursInMilli;

				long elapsedMinutes = different / minutesInMilli;
				different = different % minutesInMilli;

				long elapsedSeconds = different / secondsInMilli;

				String timePart = computeTime(context, temp[1]);

				// From more than week ago, (ex: 4 Apr at 12:30 am).
				if(elapsedDays >= 7){

					finalDate = getDateIn_DDEEE(notifDate) +
							context.getResources().getString(R.string.at) +
							" " + context.getResources().getString(R.string.hour) + " " + timePart;
				}
				// From one day ago, (ex: yesterday at 12:30 am).
				else if(elapsedDays == 1){

					finalDate = context.getResources().getString(R.string.yesterday) +
							context.getResources().getString(R.string.at) +
							" " + context.getResources().getString(R.string.hour) + " ";

					finalDate += timePart;
				}
				// From less than week ago, (ex: Fri, at 12:30 am).
				else if(elapsedDays < 7 && elapsedDays > 1 ) {

					String dayOfWeek = (String) android.text.format.DateFormat.format("EEE",
							notifDate);

					if(dayOfWeek.equalsIgnoreCase("sat"))
						dayOfWeek = daysOfWeek[0];
					else if(dayOfWeek.equalsIgnoreCase("sun"))
						dayOfWeek = daysOfWeek[1];
					else if(dayOfWeek.equalsIgnoreCase("mon"))
						dayOfWeek = daysOfWeek[2];
					else if(dayOfWeek.equalsIgnoreCase("tue"))
						dayOfWeek = daysOfWeek[3];
					else if(dayOfWeek.equalsIgnoreCase("wed"))
						dayOfWeek = daysOfWeek[4];
					else if(dayOfWeek.equalsIgnoreCase("thu"))
						dayOfWeek = daysOfWeek[5];
					else if(dayOfWeek.equalsIgnoreCase("fri"))
						dayOfWeek = daysOfWeek[6];

					finalDate = dayOfWeek + context.getResources().getString(R.string.at) +
							" " + context.getResources().getString(R.string.hour) + " ";

					finalDate += timePart;

				}
				// Today, (ex: 2 hours ago.
				else if(elapsedHours != 0){
					finalDate = elapsedHours + " " + context.getResources().getString(
							R.string.hours_ago);
				}
				// Today, (ex: 2 minutes ago.
				else if(elapsedMinutes != 0){
					finalDate = elapsedMinutes + " " + context.getResources().getString(
							R.string.minutes_ago);
				}
				// Today, (ex: 2 seconds ago.
				else if(elapsedSeconds != 0){
					finalDate = elapsedSeconds + " " + context.getResources().getString(
							R.string.seconds_ago);
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return finalDate;
	}

	public static void initParameters(Activity activity) {
		daysOfWeek = activity.getResources().getStringArray(R.array.days_of_weeks);
		months = activity.getResources().getStringArray(R.array.months);

		currentDate = Utilities.getCurrentDate(Keys.CONST_NOTIFICATION_DATE_FORMAT);
	}
}
