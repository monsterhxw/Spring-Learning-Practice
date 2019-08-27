package geektime.spring.mybatis.pagehelper.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * 在 Money 与 Long 之间转换的 TypeHandler，处理 CNY 人民币
 */
public class MoneyTypeHandler extends BaseTypeHandler<Money> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int parameterIndex, Money money, JdbcType jdbcType) throws SQLException {
        preparedStatement.setLong(parameterIndex, money.getAmountMinorLong());
    }

    @Override
    public Money getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return parseMoney(resultSet.getLong(columnName));
    }

    @Override
    public Money getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return parseMoney(resultSet.getLong(columnIndex));
    }

    @Override
    public Money getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return parseMoney(callableStatement.getLong(columnIndex));
    }

    private Money parseMoney(Long value) {
//        return Money.of(CurrencyUnit.of("CNY"), value / 100.0);
        return Money.ofMinor(CurrencyUnit.of("CNY"), value);
    }

}
