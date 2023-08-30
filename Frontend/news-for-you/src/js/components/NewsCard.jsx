import { Card } from "antd";
import React from "react";

function NewsCard({ news }) {
  const getTime = (time) => {
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
  };
  return (
    <Card
      className="news-card"
      onClick={() => {
        window.location.href = news.newsLink;
      }}
    >
      <div className="news-title">{news.newsTitle}</div>
      <div
        className="news-description"
        dangerouslySetInnerHTML={{
          __html: news.newsDescription.replace("<br/>", ""),
        }}
      />
      <div className="posted-on">
        Posted On <span>{getTime(news.newsPublishDateTime)}</span>
      </div>
    </Card>
  );
}

export default NewsCard;
