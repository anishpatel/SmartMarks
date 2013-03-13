
public class TokenValue implements Comparable<TokenValue>
{
	public final String token;
	public final Number value;
	
	public TokenValue(String token, Number value)
	{
		this.token = token;
		this.value = value;
	}
	
	public int compareTo(TokenValue other)
	{
		int cmp = Double.compare(this.value.doubleValue(), other.value.doubleValue());
		return cmp != 0 ? cmp : this.token.compareTo(other.token);
	}
}
