package br.com.tomcode.iodataflow;

public enum DateFormat {
	
		AAAAMMDD("yyyyMMdd"),
		AAAA_MM_DD("yyyy-MM-dd"),
		AAMMDD("yyMMdd"),
		AA_MM_DD("yy-MM-dd"),
		DDMMAAAA("ddMMyyyy"),
		DD_MM_AAAA("dd-MM-yyyy"),
		DDMMAA("ddMMyy"),
		DD_MM_AA("dd-MM-yy"),
		DD_MM_AAbr("dd/MM/yy"),
		DD_MM_AAAAbr("dd/MM/yyyy"),
		MMAAAA("MMyyyy"),
		AAAAMM("yyyyMM"),
		MM_AAAAbr("MM/yyyy"),
		AAAA_MMbr("yyyy/MM");
		
		private String pattern;

		private DateFormat(String pattern){
			this.pattern = pattern;
		}
		
		public String getPattern(){
			return this.pattern;
		}
	
}
