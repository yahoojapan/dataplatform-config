/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.co.yahoo.dataplatform.config;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by shusuzuk on 9/29/16.
 */
public class ConfigurationTest {
  @Test
  public void test_set(){
    Configuration conf = new Configuration();
    String key = "test_key";
    String value = "test_value";

    conf.set(key, value);
    assertEquals(value, conf.get(key));
  }

  @Test
  public void test_add(){
    Configuration conf = new Configuration();

    // add properties
    Properties properties = new Properties();
    properties.put("prop_key", "prop_val");
    conf.add(properties);

    // add Map.Entity
    Map<String, String> map = new HashMap<>();
    map.put("map_key", "map_val");
    conf.add(map.entrySet());

    // add other Configration
    Configuration other = new Configuration();
    other.set("conf_key", "conf_val");
    conf.add(other);

    assertEquals(conf.get("prop_key"), "prop_val");
    assertEquals(conf.get("map_key"), "map_val");
    assertEquals(conf.get("conf_key"), "conf_val");
  }

  @Test
  public void test_clear(){
    Configuration conf = new Configuration();
    String key = "test_key";
    String value = "test_value";
    conf.set(key, value);
    assertEquals(conf.getKey().size(), 1);
    conf.clear();
    assertEquals(conf.getKey().size(), 0);
  }

  @Test
  public void test_getMethods() throws IOException{
    Configuration conf = new Configuration();
    conf.set("int", Integer.toString(Integer.MAX_VALUE));
    conf.set("long", Long.toString(Long.MAX_VALUE));
    conf.set("double", Double.toString(Double.MAX_VALUE));
    conf.set(String.class.getName(), String.class.getName());

    // get values
    assertEquals(conf.getInt("int"), Integer.MAX_VALUE);
    assertEquals(conf.getLong("long"), Long.MAX_VALUE);
    assertEquals(conf.getDouble("double"), Double.MAX_VALUE);
    assertEquals(conf.getObject(String.class.getName()).toString(), "");

    conf.clear();

    // get default values
    assertEquals(conf.getInt("int_default", 3), 3);
    assertEquals(conf.getLong("long_default", 50), (long) 50);
    assertEquals(conf.getDouble("double_default", 0.05), 0.05);
    assertEquals(conf.getObject(Long.class.getName(), String.class.getName()).toString(), "");
  }

  @Test
  public void test_containsKey() throws IOException{
    Configuration conf = new Configuration();
    conf.set("foo", "bar");
    assertTrue(conf.containsKey("foo"));
    assertFalse(conf.containsKey("test"));
  }

}
