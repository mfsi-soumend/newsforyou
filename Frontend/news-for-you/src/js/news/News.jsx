import React, { useEffect, useState } from "react";
import NewsCard from "../components/NewsCard";
import { SmileOutlined } from "@ant-design/icons";
import NewsService from "../sevices/newsService";
import { Result, Skeleton } from "antd";

function News({ selectedCategories }) {
  const [allNews, setAllNews] = useState([]);
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    let categories = localStorage.getItem("selectedCategories");
    let payload = categories ? categories.split(",") : [];
    setLoading(true);
    NewsService.getAllNews(payload)
      .then((res) => {
        setAllNews(res.data.data.newsList);
        setLoading(false);
      })
      .catch((err) => {
        setLoading(false);
        console.log(err);
      });
  }, [selectedCategories]);
  return (
    <div className="news-container">
      {loading ? (
        <Skeleton active />
      ) : allNews.length === 0 ? (
        <Result
          icon={<SmileOutlined />}
          title="No news to display right now !!! Our team is collecting news for you. Stay connected :)"
        />
      ) : (
        <div className="news-card-wrapper">
          {allNews.map((news) => {
            return <NewsCard news={news} id={news.newsId} key={news.newsId} />;
          })}
        </div>
      )}
    </div>
  );
}

export default News;
