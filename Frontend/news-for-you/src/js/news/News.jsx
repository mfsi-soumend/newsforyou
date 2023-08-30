import React, { useEffect, useState } from "react";
import NewsCard from "../components/NewsCard";
import NewsService from "../sevices/newsService";

function News({ selectedCategories }) {
  const [allNews, setAllNews] = useState([]);
  useEffect(() => {
    let categories = localStorage.getItem("selectedCategories");
    let payload = categories ? categories.split(",") : [];
    NewsService.getAllNews(payload)
      .then((res) => {
        setAllNews(res.data.data.newsList);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [selectedCategories]);
  return (
    <div className="news-card-wrapper">
      {allNews.map((news) => {
        return <NewsCard news={news} id={news.newsId} key={news.newsId} />;
      })}
    </div>
  );
}

export default News;
