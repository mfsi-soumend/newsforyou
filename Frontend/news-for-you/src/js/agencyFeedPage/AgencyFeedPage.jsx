import React, { useEffect, useState } from "react";
import AgencyService from "../sevices/agencyService";
import { SmileOutlined } from "@ant-design/icons";
import { useParams } from "react-router-dom";
import { Divider, Result, Skeleton } from "antd";
import NewsService from "../sevices/newsService";
import CategoryService from "../sevices/categoryService";

function AgencyFeedPage() {
  const [agency, setAgency] = useState();
  const [category, setCategory] = useState();
  const [readMore, setReadMore] = useState(null);
  const [loading, setLoading] = useState(true);
  const [allNews, setAllNews] = useState([]);
  let { id } = useParams();

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
    AgencyService.getAgencyFeed(id)
      .then((res) => {
        AgencyService.getAgency(res.data.data.agencyId).then((res) => {
          setAgency(res.data.data);
        });
        CategoryService.getCategory(res.data.data.categoryId).then((res) => {
          setCategory(res.data.data);
        });
        let payload = {
          categoryId: res.data.data.categoryId,
          agencyId: res.data.data.agencyId,
        };
        NewsService.getAgencyFeedNews(payload).then((res2) => {
          console.log(res2);
          setAllNews(res2.data.data.newsList);
          setLoading(false);
        });
      })
      .catch((err) => {});
  }, []);

  return (
    <div style={{ minHeight: "82vh", padding: "50px" }}>
      {loading ? (
        <>
          <Skeleton active />
          <Skeleton active />
          <Skeleton active />
        </>
      ) : (
        <>
          <div
            className="admin-right-section-title"
            style={{
              display: "flex",
              alignItems: "center",
              borderBottom: "3px solid #ffffff",
              paddingBottom: "30px",
              margin: "0 50px 40px",
              justifyContent: "center",
            }}
          >
            {agency.agencyLogoPath ? (
              <span>
                <img src={agency.agencyLogoPath} alt={agency.agencyName} />
              </span>
            ) : null}
            {agency.agencyName}
            {" || "}
            {category.categoryTitle}
          </div>
          {allNews.length === 0 ? (
            <Result
              icon={<SmileOutlined />}
              title="No news to display right now !!! Our team is collecting news for you. Stay connected :)"
            />
          ) : (
            <div style={{ margin: "0 75px" }}>
              {allNews.map((news) => {
                return (
                  <>
                    <div
                      className="news-page-title"
                      style={{ maxWidth: "100%" }}
                    >
                      {news.newsTitle}
                    </div>
                    <div
                      className={
                        readMore !== news.newsId
                          ? "news-page-description news-description-feed"
                          : "news-page-description"
                      }
                      dangerouslySetInnerHTML={{
                        __html: news.newsDescription,
                      }}
                      style={{ maxWidth: "100%" }}
                    />

                    {readMore !== news.newsId ? (
                      <div
                        className="read-more"
                        onClick={() => {
                          setReadMore(news.newsId);
                        }}
                      >
                        read more
                      </div>
                    ) : (
                      <div
                        className="read-more"
                        onClick={() => {
                          setReadMore(null);
                        }}
                      >
                        read less
                      </div>
                    )}

                    <div
                      className="posted-on"
                      style={{ margin: "30px 0 50px 20px" }}
                    >
                      Posted On <span>{getTime(news.newsPublishDateTime)}</span>
                    </div>
                    <Divider />
                  </>
                );
              })}
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default AgencyFeedPage;
