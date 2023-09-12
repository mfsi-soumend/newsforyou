import React, { useEffect, useState } from "react";
import NewsService from "../sevices/newsService";
import { Tag, Skeleton, Space } from "antd";
import AgencyService from "../sevices/agencyService";
import util from "../utils/util";

function NewsPage({ id }) {
  const [news, setNews] = useState();
  const [agency, setAgency] = useState();

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
        Posted On <span>{util.getTime(news.newsPublishDateTime)}</span>
      </div>
    </>
  ) : (
    <Skeleton active />
  );
}

export default NewsPage;
