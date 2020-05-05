package com.uetty.sample.springboot.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;

import java.io.IOException;

/**
 * 
 */
@SuppressWarnings("unused")
public class JacksonUtil {

    // ObjectMapper new的开销比较大，设置两个用于复用的实例
    // 常用的用于复用的实例1
    public static JacksonUtil jackson = new JacksonUtil().withIgnoreUnknownPro().withDisFailUnknown();

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Float反序列化成BigDECIMAL
     */
    public JacksonUtil witDishBigForFloats() {
        mapper.disable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        return this;
    }

    /**
     * 反序列化允许null
     */
    public JacksonUtil withDisAcceptNull() {
        mapper.disable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        return this;
    }

    /**
     * 反序列化允许null,String ""
     */
    public JacksonUtil withDisAcceptStringNull() {
        mapper.disable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return this;
    }

    /**
     * Float强转成int
     */
    public JacksonUtil withDisFloatAsInt() {
        mapper.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
        return this;
    }

    /**
     * String[]强转成数组
     */
    public JacksonUtil withDisStringAsArray() {
        mapper.disable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return this;
    }

    /**
     * date转化成timeZone
     */
    public JacksonUtil withDisDataAsTimeZone() {
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return this;
    }

    public JacksonUtil withDisFETCH() {
        mapper.disable(DeserializationFeature.EAGER_DESERIALIZER_FETCH);
        return this;
    }

    /**
     * 失败忽略
     */
    public JacksonUtil withDisFailIgnoged() {
        mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        return this;
    }

    /**
     * 未知属性
     */
    public JacksonUtil withDisFailUnknown() {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return this;
    }

    /**
     * 没定义的属性忽略
     */
    public JacksonUtil withIgnoreUnknown() {
        mapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        return this;
    }

    /**
     * 没定义的属性忽略
     */
    public JacksonUtil withIgnoreUnknownPro() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return this;
    }

    /**
     * 用科学计数法表示
     */
    public JacksonUtil withBigAsPlain() {
        mapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        return this;
    }

    /**
     *           *确定解析器是否允许使用的功能           *未引用的字段名称（由Javascript允许，
     * *但不是JSON规范）。           *由于JSON规范要求使用双引号           *字段名称，
     * *这是非标准功能，默认情况下禁用。
     */
    public JacksonUtil withAllowFieldNames() {
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        return this;
    }

    /**
     * 确定解析器是否允许的功能 JSON字符串包含无引号的控制字符（ASCII字符的值小于32，包括标签和换行字符）。
     * 如果将feature设置为false，则会抛出异常遇到字符由于JSON规范要求引用所有控制字符，这是非标准功能，默认情况下禁用。
     */
    public JacksonUtil withAllowControlChars() {
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
        return this;
    }

    /**
     * 确定解析器是否允许使用的功能单引号（撇号，字符“\”）为引用字符串（名称和字符串值）。
     * 如果是这样，这是除了其他可接受的标记之外。但不是JSON规范）。 由于JSON规范要求使用双引号字段名称，这是非标准功能，默认情况下禁用。
     */
    public JacksonUtil withAllowSingleQuotes() {
        mapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        return this;
    }

    public JacksonUtil withDefualtTyping(TypeResolverBuilder<?> typeResolverBuilder) {
        typeResolverBuilder.init(JsonTypeInfo.Id.CLASS, null);
        typeResolverBuilder.inclusion(JsonTypeInfo.As.PROPERTY);
        mapper.setDefaultTyping(typeResolverBuilder);
        return this;
    }

    /**
     * 伪值用于表示较高级别的默认值 意义上，避免超越包容性价值。 例如，如果返回           对于属性，这将使用包含的类的默认值
     * 财产，如有任何定义; 如果没有定义，那么 全局序列化包含细节。
     */
    public JacksonUtil withUseDefaults() {
        mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
        return this;
    }

    /**
     * 表示所有的属性
     */
    public JacksonUtil withAll() {
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return this;
    }

    /**
     * 表示只有具有值的属性
     */
    public JacksonUtil withNotEmpty() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return this;
    }

    /**
     * 通常可以构建专门的文本对象的方法
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public JacksonUtil withOverrideAccess() {
        mapper.getSerializationConfig().canOverrideAccessModifiers();
        return this;
    }

    /**
     * 访问者确定是否可以尝试强制重写访问
     */
    public JacksonUtil withCompileString(String src) {
        mapper.getSerializationConfig().compileString(src);
        return this;
    }

    /**
     * 配置对象工厂
     */
    public JacksonUtil withConstruct() {
        mapper.getSerializationConfig().constructDefaultPrettyPrinter();
        return this;
    }

    /**
     * 设置跟节点名称
     */
    public JacksonUtil withRootName(String rootName) {
        mapper.getSerializationConfig().withRootName(rootName);
        return this;
    }

    /**
     * 表示仅属性为非空的值
     */
    public JacksonUtil withNotNull() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return this;
    }

    /**
     * 是否缩放排列输出
     */
    public JacksonUtil withOrder() {
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return this;
    }

    /**
     * 是否环绕根元素(以类名作为根元素) 默认是true
     */
    public JacksonUtil withRoot() {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return this;
    }

    /**
     * 转化成全小写
     */
    public JacksonUtil with2Lower() {
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
        return this;
    }

    /**
     * 序列化日期时以timestamps
     */
    public JacksonUtil withTimestamps() {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        return this;
    }

    /**
     * 将枚举以String输出
     */
    public JacksonUtil withEnum2String() {
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, false);
        return this;
    }

    /**
     * 将枚举以Ordinal输出
     */
    public JacksonUtil withEnum2Ordinal() {
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
        return this;
    }

    /**
     * 单个元素的数组不以数组输出
     */
    public JacksonUtil withArray() {
        mapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
        return this;
    }

    /**
     * 序列化Map时对key进行排序操作
     */
    public JacksonUtil withMapOrder() {
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        return this;
    }

    /**
     * 序列化char[]时以json数组输出
     */
    public JacksonUtil withChar() {
        mapper.configure(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS, true);
        return this;
    }

    /**
     * 将Object对象转化成json
     */
    public String obj2Json(Object obj) throws JsonProcessingException {
        if (obj == null) {
            return null;
        }
        return mapper.writeValueAsString(obj);
    }

    /**
     * 将Object对象转化成byte数组
     */
    public byte[] obj2Byte(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsBytes(obj);
    }

    /**
     * 将json转化成Obj
     */
    public <T> T json2Obj(String json) throws IOException {
        if (json == null) {
            return null;
        }
        return mapper.readValue(json, new TypeReference<T>() {});
    }

    /**
     * 将byte数组转换成Obj
     */
    public <T> T byte2Obj(byte[] by) throws IOException {
        return mapper.readValue(by, new TypeReference<T>() {});
    }

    /**
     * 将json转化成bean对象
     */
    public <T> T json2Obj(String json, Class<T> t) throws IOException {
        return mapper.readValue(json, t);
    }

    public <T> T bean2Obj(Object obj) {
        try {
            return mapper.readValue(mapper.writeValueAsString(obj), new TypeReference<T>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将obj转换成对象
     */
    public <T> T obj2Bean(Object obj, Class<T> t) throws IOException {
        return mapper.readValue(mapper.writeValueAsString(obj), t);
    }

    /**
     * 将byte数组转换成对象
     */
    public <T> T byte2Bean(byte[] src, Class<T> t) throws IOException {
        return mapper.readValue(src, t);
    }

    public void printJson(Object obj) throws JsonProcessingException {
        System.out.println(obj2Json(obj));
    }

    public void printJson(String json) throws IOException {
        System.out.println(obj2Json(json2Obj(json)));
    }

    public void printByte(byte[] data) throws IOException {
        System.out.println(obj2Json(byte2Obj(data)));
    }
}
