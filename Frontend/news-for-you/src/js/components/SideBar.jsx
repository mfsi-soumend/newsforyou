import { Card, Checkbox, Layout } from "antd";
import React, { useEffect, useState } from "react";
import CategoryService from "../sevices/categoryService";

const { Sider } = Layout;

function SideBar({ colorBgContainer, onCategoryChange }) {
  const [options, setOptions] = useState([]);
  const [selected, setSelected] = useState([]);
  const onChange = (value) => {
    onCategoryChange(value);
    setSelected(value);
  };
  useEffect(() => {
    const sel = localStorage.getItem("selectedCategories");
    if (sel) {
      setSelected(sel.split(","));
    }
    CategoryService.getAllCategory().then((res) => {
      let optionList = [];
      res.data.data.categoryResponseList.map((category) => {
        optionList.push({
          label: category.categoryTitle,
          value: category.categoryId,
        });
      });
      setOptions(optionList);
    });
  }, []);
  return (
    <Sider style={{ background: colorBgContainer }} width={200}>
      <Card className="filter-card">
        <div className="category-title">Select Category</div>
        <div className="category-filter-wrapper">
          <Checkbox.Group
            options={options}
            value={selected}
            onChange={onChange}
            className="category-filter"
          />
        </div>
      </Card>
    </Sider>
  );
}

export default SideBar;
