
public class TokenValue implements Comparable<TokenValue>
{
	public final int tokenId;
	public final Number value;
	
	public TokenValue(int tokenId, Number value)
	{
		this.tokenId = tokenId;
		this.value = value;
	}
	
	public int compareTo(TokenValue other)
	{
		int cmp = Double.compare(this.value.doubleValue(), other.value.doubleValue());
		return cmp != 0 ? cmp : Integer.compare(this.tokenId, other.tokenId);
	}
}
