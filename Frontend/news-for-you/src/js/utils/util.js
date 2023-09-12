const util = {
  getTime(time) {
    const utcDateTimeString = time;
    const utcDateTime = new Date(utcDateTimeString);

    // Convert to local date and time
    const localYear = utcDateTime.getFullYear();
    const localMonth = utcDateTime.getMonth() + 1; // Months are zero-indexed
    const localDay = utcDateTime.getDate();
    const localHours = utcDateTime.getHours();
    const localMinutes = utcDateTime.getMinutes();
    const localSeconds = utcDateTime.getSeconds();
    return `${localYear}-${localMonth.toString().padStart(2, "0")}-${localDay
      .toString()
      .padStart(2, "0")} ${localHours
      .toString()
      .padStart(2, "0")}:${localMinutes
      .toString()
      .padStart(2, "0")}:${localSeconds.toString().padStart(2, "0")}`;
  },
  getRandomColor() {
    const letters = "0123456789ABCDEF";
    let color = "#";
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  },
};
export default util;
