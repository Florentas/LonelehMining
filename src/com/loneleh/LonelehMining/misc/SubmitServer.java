package com.loneleh.LonelehMining.misc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.loneleh.LonelehMining.script.LonelehMining;
import com.loneleh.LonelehMining.script.mining.MiningVars;
import com.loneleh.LonelehMining.script.mining.Ore;

public class SubmitServer
{
	private final String submitFile = "/submit.php";
	private final String signatureFile = "/signature.php";
	private final String logFile = "/gtfo/log.php";
	
	@SuppressWarnings("unused")
	private String submitUrl = "";
	@SuppressWarnings("unused")
	private String signatureUrl = "";
	private String logUrl = "";
	
	private long lastTime = 0; //in millis
	private long lastOres = 0;
	private long lastGems = 0;
	private long lastProfit = 0;
	private double lastExp = 0;
	
	public SubmitServer()
	{
		new SubmitServer("http://loneleh.comxa.com/lonelehmining");
	}
	
	public SubmitServer(String url)
	{
		this.setUrl(url, submitFile, signatureFile, logFile);
	}
	
	public String submit()
	{
		String[] submitParameters = new String[] {
				"script", "mining",
				"name", LonelehMining.getPbName(),
				"time", String.format("%d", (Variables.miningTimer.getElapsedTime()-lastTime)),
				"ores", String.format("%d", (MiningVars.oresMined-lastOres)),
				"gems", String.format("%d", (MiningVars.gemsMined-lastGems)),
				"profit", String.format("%d", (Ore.getTotalProfit()-lastProfit)),
				"exp", String.format("%.1f", (Ore.getTotalExp()-lastExp))};
		
		lastTime = Variables.miningTimer.getElapsedTime();
		lastOres = MiningVars.oresMined;
		lastGems = MiningVars.gemsMined;
		lastProfit = Ore.getTotalProfit();
		lastExp = Ore.getTotalExp();
		
		return submit(logUrl, submitParameters);
	}
	
	/**
	 * uses http get to submit data to the specified server url
	 * @param endpoint your url
	 * @param requestParameters field1, val1, field2, val2, etc
	 * @return the response from server
	 */
	private String submit(String endpoint, final String ...requestParameters)
	{
		String result = null;
		
		// Send a GET request to the servlet
		try
		{
			String urlStr = endpoint + "?";
			if (requestParameters != null && requestParameters.length-1 > 0)
			{
				for (int i = 0 ; i < requestParameters.length ; i++)
				{
					if (requestParameters[i].equalsIgnoreCase("time"))
					{
						urlStr += requestParameters[i] + "=" + (Long.parseLong(requestParameters[++i])) + "&";
					}
					else if (requestParameters[i].equalsIgnoreCase("ores"))
					{
						urlStr += requestParameters[i] + "=" + (Long.parseLong(requestParameters[++i])) + "&";
					}
					else if (requestParameters[i].equalsIgnoreCase("gems"))
					{
						urlStr += requestParameters[i] + "=" + (Long.parseLong(requestParameters[++i])) + "&";
					}
					else if (requestParameters[i].equalsIgnoreCase("profit"))
					{
						urlStr += requestParameters[i] + "=" + (Long.parseLong(requestParameters[++i])) + "&";
					}
					else if (requestParameters[i].equalsIgnoreCase("exp"))
					{
						urlStr += requestParameters[i] + "=" + (Double.parseDouble(requestParameters[++i])) + "&";
					}
					else
					{
						urlStr += requestParameters[i] + "=" + requestParameters[++i] + "&";
					}
				}
			}
			
			URL url = new URL(urlStr);
			URLConnection submitConn = url.openConnection();
			
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(submitConn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			
			while ((line = rd.readLine()) != null)
				sb.append(line);
			
			rd.close();
			
			result = sb.toString().replaceAll("\\<.*?\\>", "");
			
			
			if (result.contains("*") || result.contains("error") || result.contains("Error") || result.contains("ERROR"))
			{
				LonelehMining.logger.severe(result);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LonelehMining.logger.severe("An error has occured while pushing data to server: " + e.getCause());
		}
		return result;
	}
	
	public long getLastTime()
	{
		return lastTime;
	}
	
	public void setUrl(String newUrl, String submit, String signature, String log)
	{
		if (!newUrl.startsWith("http://"))
			newUrl = "http://" + newUrl;
		
		this.submitUrl = newUrl + submit;
		this.signatureUrl = newUrl + signature;
		this.logUrl = newUrl + log;
	}
}
