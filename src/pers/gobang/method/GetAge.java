package pers.gobang.method;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class GetAge {
	/**
	 * 根据生日算出虚岁
	 *
	 * @param birthday 需要进行计算的生日
	 * @return 返回int型的年龄
	 */
	public int getAgeByBirth(Date birthday) {
		int age;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());
			Calendar birth = Calendar.getInstance();
			birth.setTime(birthday);
			if (birth.after(now)) {
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
					age += 1;
				}
			}
			return age;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * localDate转Date
	 *
	 * @param localDate 目标LocalDated数据
	 * @return 返回Data型数据
	 */
	public Date toDate(LocalDate localDate) {
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
		Instant instant1 = zonedDateTime.toInstant();
		return Date.from(instant1);
	}
}
