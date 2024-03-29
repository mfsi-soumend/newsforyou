import { Tabs, Layout } from "antd";
import React, { useEffect } from "react";
import Category from "../components/Category";
import {
  AppstoreTwoTone,
  IdcardTwoTone,
  BookTwoTone,
  TagsTwoTone,
} from "@ant-design/icons";
import Agency from "../components/Agency";
import AgencyFeed from "../components/AgencyFeed";
import ManageNews from "../components/ManageNews";
const { Content } = Layout;

function Dashboard() {
  useEffect(() => {
    if (!localStorage.getItem("adminUserToken")) {
      window.location.href = "/login";
    }
  }, []);
  return (
    <Content className="tab-wrapper" style={{ padding: "24px 48px 0 48px" }}>
      <Tabs
        defaultActiveKey="category"
        tabPosition="left"
        destroyInactiveTabPane
        className="admin-tabs"
        items={[
          {
            label: (
              <span>
                <TagsTwoTone />
                Category
              </span>
            ),
            key: "category",
            children: <Category />,
          },
          {
            label: (
              <span>
                <IdcardTwoTone />
                Agency
              </span>
            ),
            key: "agency",
            children: <Agency />,
          },
          {
            label: (
              <span>
                <AppstoreTwoTone />
                Agency Feed
              </span>
            ),
            key: "agency_feed",
            children: <AgencyFeed />,
          },
          {
            label: (
              <span>
                <BookTwoTone />
                Manage News
              </span>
            ),
            key: "news",
            children: <ManageNews />,
          },
        ]}
      />
    </Content>
  );
}

export default Dashboard;
