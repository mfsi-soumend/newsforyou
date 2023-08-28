import { Card, Checkbox, Layout } from "antd";
import React from "react";

const { Sider } = Layout;

function SideBar({ colorBgContainer }) {
  const onChange = (values) => {
    console.log(values);
  };
  const options = [
    {
      label: "Apple",
      value: "Apple",
    },
    {
      label: "Pear",
      value: "Pear",
    },
    {
      label: "Orange",
      value: "Orange",
    },
    {
      label: "Apple",
      value: "Apple",
    },
    {
      label: "Pear",
      value: "Pear",
    },
    {
      label: "Orange",
      value: "Orange",
    },
    {
      label: "Pear",
      value: "Pear",
    },
    {
      label: "Orange",
      value: "Orange",
    },
  ];
  return (
    <Sider style={{ background: colorBgContainer }} width={200}>
      <Card className="filter-card">
        <div className="category-title">Select Category</div>
        <div className="category-filter-wrapper">
          <Checkbox.Group
            options={options}
            onChange={onChange}
            className="category-filter"
          />
        </div>
      </Card>
    </Sider>
  );
}

export default SideBar;
