import React, { useEffect, useState } from "react";
import NewsService from "../sevices/newsService";
import { Tag, Skeleton, Space } from "antd";
import AgencyService from "../sevices/agencyService";

function NewsPage({ id }) {
  const [news, setNews] = useState();
  const [agency, setAgency] = useState();

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

  useEffect(() => {
    if (id) {
      NewsService.getNews(id)
        .then((res) => {
          setNews(res.data.data);
          AgencyService.getAgency(res.data.data.agencyId).then((res) => {
            setAgency(res.data.data);
          });
        })
        .catch(() => {
          window.location.href = "/pageNotFound";
        });
    }
  }, []);
  return news ? (
    <>
      <div className="news-page-title">{news.newsTitle}</div>
      <div
        className="news-page-description"
        dangerouslySetInnerHTML={{
          __html: news.newsDescription,
        }}
      />
      <div className="news-tags">
        <Space>
          <Tag color="magenta" className="news-page-tags">
            {news.categoryId}
          </Tag>
          {agency ? (
            <Tag color="cyan" className="news-page-tags">
              {agency.agencyName}
            </Tag>
          ) : null}
        </Space>
      </div>
      <div className="posted-on" style={{ margin: "30px 0 50px 20px" }}>
        Posted On <span>{getTime(news.newsPublishDateTime)}</span>
      </div>
    </>
  ) : (
    <Skeleton active />
  );
}

export default NewsPage;
